package fr.poulpogaz.isekai.editor.process;

import fr.poulpogaz.isekai.editor.utils.concurrent.ExecutorWithException;
import fr.poulpogaz.isekai.editor.utils.concurrent.NamedThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class CommandExecutor {

    private static final Logger LOGGER = LogManager.getLogger(CommandExecutor.class);

    private static final ThreadPoolExecutor executor;

    private final CompletableFuture<Void> task;
    private final List<String> errorOutput;
    private final List<String> standardOutput;
    private final List<String> output;

    private int next = 0;
    private int nextStandard = 0;
    private int nextError = 0;

    public CommandExecutor(String... args) throws IOException {
        LOGGER.info("Executing new command: " + Arrays.toString(args));

        errorOutput = Collections.synchronizedList(new ArrayList<>());
        standardOutput = Collections.synchronizedList(new ArrayList<>());
        output = Collections.synchronizedList(new ArrayList<>());

        Process process = createProcess(args);

        task = CompletableFuture.allOf(
                constructTask(process.getInputStream(), LOGGER::info, standardOutput),
                constructTask(process.getErrorStream(), LOGGER::warn, errorOutput));
    }

    private Process createProcess(String... args) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(args);

        return builder.start();
    }

    private CompletableFuture<?> constructTask(final InputStream in, Consumer<String> logger, List<String> output) {
        return CompletableFuture.runAsync(() -> {
            InputStreamReader isr = new InputStreamReader(in, StandardCharsets.ISO_8859_1);

            BufferedReader br = new BufferedReader(isr);

            try {
                String line;
                while ((line = br.readLine()) != null) {
                    this.output.add(line);
                    output.add(line);
                    logger.accept(line);
                }

                isr.close();
                br.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }, executor);
    }

    public String next() {
        while (true) {
            if (hasNext()) {

                String nextStr = output.get(next);
                next++;

                return nextStr;
            }
        }
    }

    public boolean hasNext() {
        return next != output.size();
    }

    public String nextStandard() {
        while (true) {
            if (hasNextStandard()) {

                String nextStr = standardOutput.get(nextStandard);
                nextStandard++;

                return nextStr;
            }
        }
    }

    public boolean hasNextStandard() {
        return nextStandard != standardOutput.size();
    }

    public String nextError() {
        while (true) {
            if (hasNextError()) {

                String nextStr = errorOutput.get(nextError);
                nextError++;

                return nextStr;
            }
        }
    }

    public boolean hasNextError() {
        return nextError != errorOutput.size();
    }

    public boolean isDone() {
        return task.isDone();
    }

    public String[] get() {
        try {
            task.get(); // Finish all task
            return output.toArray(new String[0]);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getError() {
        try {
            task.get(); // Finish all task
            return errorOutput.toArray(new String[0]);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getStandardOutput() {
        try {
            task.get(); // Finish all task
            return standardOutput.toArray(new String[0]);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CompletableFuture<Void> getTask() {
        return task;
    }

    static {
        executor = (ThreadPoolExecutor) ExecutorWithException.newFixedThreadPool(2, new NamedThreadFactory("ExportThread"), 1, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
    }
}
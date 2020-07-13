package fr.poulpogaz.isekai.editor.utils.concurrent;

import java.util.Objects;
import java.util.concurrent.*;

public class TaskGroup {

    protected final String name;
    protected final ExecutorService executor;
    protected int maxThread;

    public TaskGroup(String name) {
        this(name, new DaemonThreadFactory(name), 0);
    }

    public TaskGroup(String name, int maxThread) {
        this(name, new DaemonThreadFactory(name), maxThread);
    }

    public TaskGroup(String name, ThreadFactory factory, int maxThread) {
        this.name = Objects.requireNonNull(name, "TaskGroup identifier must be specified");
        this.maxThread = maxThread;

        executor = createExecutor(factory);
    }

    protected ExecutorService createExecutor(ThreadFactory factory) {
        if (maxThread == 0) {
            return Executors.newCachedThreadPool(factory);
        } else if (maxThread == 1) {
            return Executors.newSingleThreadExecutor(factory);
        } else {
            return Executors.newFixedThreadPool(maxThread, factory);
        }
    }

    public Future<?> execute(Runnable runnable) {
        return executor.submit(runnable);
    }

    public <T> Future<T> execute(Callable<T> callable) {
        return executor.submit(callable);
    }

    public void shutdown() {
        synchronized (TaskGroup.this) {
            executor.shutdown();
        }
    }

    public void shutdownNow() {
        synchronized (TaskGroup.this) {
            executor.shutdownNow();
        }
    }

    public String getName() {
        return name;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public int getMaxThread() {
        return maxThread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskGroup)) return false;

        TaskGroup taskGroup = (TaskGroup) o;

        if (maxThread != taskGroup.maxThread) return false;
        if (!name.equals(taskGroup.name)) return false;
        return executor.equals(taskGroup.executor);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + executor.hashCode();
        result = 31 * result + maxThread;
        return result;
    }
}
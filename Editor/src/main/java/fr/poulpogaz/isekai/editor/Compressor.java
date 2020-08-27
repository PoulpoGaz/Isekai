package fr.poulpogaz.isekai.editor;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.stream.Stream;

// FOR INTERNAL USAGE ONLY
// SHOULD NOT BE PRESENT IN JAR FILES
public class Compressor {

    public static void compress() throws IOException {
        Path from = Path.of("file");

        compress(from, Path.of("Editor/src/main/resources/pack/default.skb"));
        compress(from, Path.of("Editor/target/classes/pack/default.skb"));
    }

    private static void compress(Path from, Path to) throws IOException {
        FileSystem system = FileSystems.newFileSystem(to, Map.of("create", "true"));

        try (Stream<Path> files = Files.walk(from)) {
            files.forEach((path) -> {
                if (path.equals(from)) {
                    return;
                }

                Path zipPath = system.getPath(path.toString().replace("file", ""));

                try {
                    if (Files.isDirectory(path)) {
                        if (!Files.exists(zipPath)) {
                            Files.createDirectory(zipPath);
                        }
                    } else {
                        Files.copy(path, zipPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        system.close();
    }
}
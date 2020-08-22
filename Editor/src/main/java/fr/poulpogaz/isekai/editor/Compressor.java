package fr.poulpogaz.isekai.editor;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.stream.Stream;

public class Compressor {

    public static void compress() throws IOException {
        Path from = Path.of("file");
        Path to = Path.of("Editor/src/main/resources/pack/default.skb");

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
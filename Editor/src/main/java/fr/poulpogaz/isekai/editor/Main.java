package fr.poulpogaz.isekai.editor;

import fr.poulpogaz.isekai.editor.pack.PackIO;
import fr.poulpogaz.json.JsonException;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        /*Cache.setRoot(System.getenv("APPDATA") + "/Isekai/editor");

        EventQueue.invokeLater(() -> {
            FlatDarculaLaf.install();

            UIManager.put("MenuItem.selectionType", "underline");

            UIManager.put("Icon.color", UIManager.getColor("Table.sortIconColor"));

            new IsekaiEditor();
        });*/

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try (FileSystem zipfs = FileSystems.newFileSystem(Path.of("file/test.skb"), env)) {
            try (Stream<Path> stream = Files.walk(Path.of("file"))) {

                stream.forEach((path) -> {
                    if (Files.isDirectory(path)) {
                        return;
                    }

                    String p = path.toString();

                    System.out.println(p);

                    if (p.equals("file\\test.skb")) {
                        return;
                    }

                    p = p.replace("file\\", "");

                    Path zipPath = zipfs.getPath(p);

                    try {
                        Files.copy(path, zipPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PackIO.deserialize(Path.of("file/test.skb"));
        } catch (IOException | JsonException e) {
            e.printStackTrace();
        }
    } // 06 33 38 05 63
}
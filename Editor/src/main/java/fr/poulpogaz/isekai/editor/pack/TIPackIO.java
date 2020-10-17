package fr.poulpogaz.isekai.editor.pack;

import java.nio.file.Files;
import java.nio.file.Path;

public class TIPackIO {

    public static void serialize(Pack pack, Path out) throws TIPackIOException {
        if (!Files.isDirectory(out)) {
            throw new TIPackIOException("Output path isn't a directory");
        }


    }
}
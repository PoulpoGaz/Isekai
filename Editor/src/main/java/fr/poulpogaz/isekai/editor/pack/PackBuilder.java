package fr.poulpogaz.isekai.editor.pack;

import com.sun.jdi.InternalException;
import fr.poulpogaz.isekai.editor.utils.Cache;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PackBuilder {

    public static Pack loadDefaultPack() {
        Path path = Cache.of("default.skb");

        try {
            if (!Files.exists(path)) {
                extract("/pack/default.skb", path);
            } else {
                byte[] out = getBytes(path);
                byte[] in = PackBuilder.class.getResourceAsStream("/pack/default.skb").readAllBytes();

                if (!Arrays.equals(out, in)) {
                    extract("/pack/default.skb", path);
                }
            }

            return PackIO.deserialize(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void extract(String from, Path to) throws IOException {
        InputStream stream = PackBuilder.class.getResourceAsStream(from);

        if (stream == null) {
            throw new InternalException("The default pack is missing");
        }

        Cache.copy(stream, to, StandardCopyOption.REPLACE_EXISTING);

        stream.close();
    }

    private static byte[] getBytes(Path path) throws IOException {
        InputStream stream = Files.newInputStream(path);

        byte[] bytes =  stream.readAllBytes();

        stream.close();

        return bytes;
    }
}
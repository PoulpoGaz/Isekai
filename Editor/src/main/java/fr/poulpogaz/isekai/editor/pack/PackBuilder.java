package fr.poulpogaz.isekai.editor.pack;

import fr.poulpogaz.isekai.editor.utils.Cache;
import fr.poulpogaz.json.JsonException;

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
                MessageDigest digest;

                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();

                    return PackIO.deserialize(path);
                }

                digest.update(getBytes(path));
                byte[] cache = digest.digest();

                digest.reset();

                digest.update(PackBuilder.class.getResourceAsStream("/pack/default.skb").readAllBytes());
                byte[] internal = digest.digest();

                if (!Arrays.equals(cache, internal)) {
                    extract("/pack/default.skb", path);
                }
            }

            return PackIO.deserialize(path);
        } catch (IOException | JsonException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void extract(String from, Path to) throws IOException {
        InputStream stream = PackBuilder.class.getResourceAsStream(from);

        if (stream == null) {
            throw new InternalError("The default pack is missing");
        }

        Cache.copy(stream, to, StandardCopyOption.REPLACE_EXISTING);
    }

    private static byte[] getBytes(Path path) throws IOException {
        InputStream stream = Files.newInputStream(path);

        return stream.readAllBytes();
    }
}
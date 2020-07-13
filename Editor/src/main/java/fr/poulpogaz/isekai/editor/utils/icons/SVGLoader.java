package fr.poulpogaz.isekai.editor.utils.icons;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class SVGLoader extends AbstractLoader {

    @Override
    public BufferedImage load(InputStream stream) throws IOException {
        byte[] inBytes = stream.readAllBytes();

        Path cachePath = getCachePath(inBytes);
        BufferedImage image = readCache(cachePath);
        if (image != null) {
            reset();
            return image;
        }

        stream = new ByteArrayInputStream(inBytes);

        BufferedImageTranscoder transcoder = new BufferedImageTranscoder();


        if (width > 0) {
            transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, (float) width);
        }
        if (height > 0) {
            transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, (float) height);
        }

        try {
            transcoder.transcode(new TranscoderInput(stream), null);
            image = transcoder.getImage();
            stream.close();

            if (processes != null) {
                for (ImagePostProcess process : processes) {
                    process.process(image);
                }
            }

            writeCache(cachePath, image);

            reset();
            return image;
        } catch (TranscoderException e) {
            throw new IOException(e);
        }
    }
}
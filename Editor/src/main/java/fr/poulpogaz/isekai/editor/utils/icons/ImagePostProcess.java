package fr.poulpogaz.isekai.editor.utils.icons;

import java.awt.image.BufferedImage;

public interface ImagePostProcess {

    void process(BufferedImage input);

    String getName();
}
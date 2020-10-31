package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.pack.image.PackImage;

import java.awt.*;

public class ImageFillTool extends FillTool  {

    private static final ImageFillTool INSTANCE = new ImageFillTool();

    private ImageFillTool() {

    }

    @Override
    protected <M extends Map<M, E>, E> boolean equals(M map, E currentElement, Point currentElementPos, E elementToBeReplaced) {
        if (map instanceof PackImage) {
            Color current = (Color) currentElement;
            Color toBeReplaced = (Color) elementToBeReplaced;

            if (current.equals(toBeReplaced)) {
                return true;
            }

            return toBeReplaced.getAlpha() == 0 && current.getAlpha() == 0;
        }

        return false;
    }

    public static ImageFillTool getInstance() {
        return INSTANCE;
    }
}
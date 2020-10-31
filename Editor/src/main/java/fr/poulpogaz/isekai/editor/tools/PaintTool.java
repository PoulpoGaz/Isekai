package fr.poulpogaz.isekai.editor.tools;

import fr.poulpogaz.isekai.editor.Map;
import fr.poulpogaz.isekai.editor.utils.icons.IconLoader;

import javax.swing.*;

public class PaintTool implements Tool {

    private static final PaintTool INSTANCE = new PaintTool();

    private PaintTool() {

    }

    @Override
    public <M extends Map<M, E>, E> void apply(M map, E element, int x, int y) {
        map.set(element, x, y);
    }

    @Override
    public Icon getIcon() {
        return IconLoader.loadSVGIcon("/icons/edit.svg");
    }

    public static PaintTool getInstance() {
        return INSTANCE;
    }
}
package fr.poulpogaz.isekai.editor.tools;

public class PaintTool implements Tool {

    private static final PaintTool INSTANCE = new PaintTool();

    private PaintTool() {

    }

    @Override
    public <M extends Map<E>, E> void apply(M map, E element, int x, int y) {
        map.set(element, x, y);
    }

    /*@Override
    public AbstractSprite getToolSprite(Pack pack, Tile tile) {
        return tile.getSprite(pack);
    }*/

    public static PaintTool getInstance() {
        return INSTANCE;
    }
}
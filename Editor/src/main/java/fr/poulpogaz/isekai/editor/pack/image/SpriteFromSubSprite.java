package fr.poulpogaz.isekai.editor.pack.image;

// A sub sprite which was converted to a sprite
public class SpriteFromSubSprite extends BasicSprite {

    private final SubSprite subSprite;

    public SpriteFromSubSprite(SubSprite subSprite) {
        super(subSprite.getName(), subSprite.getParent());

        this.subSprite = subSprite;
    }

    @Override
    public BasicSprite toSprite() {
        return this;
    }

    @Override
    public SubSprite toSubSprite() {
        return subSprite;
    }
}
package fr.poulpogaz.isekai.editor.pack.image;

import fr.poulpogaz.isekai.editor.utils.concurrent.Alarm;
import fr.poulpogaz.isekai.editor.utils.icons.AbstractIcon;

import java.awt.*;

public class SpriteIcon extends AbstractIcon {

    private final Animator animator = new Animator(null);
    private AbstractSprite sprite;

    private Alarm alarm;

    private Component component;

    public SpriteIcon(AbstractSprite sprite) {
        setSprite(sprite);
    }

    public SpriteIcon(AbstractSprite sprite, int width, int height) {
        super(width, height);
        setSprite(sprite);
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g2d) {
        component = c;

        if (animator.isRunning()) {
            animator.paint(g2d, 0, 0, width, height);
        } else {
            sprite.paint(g2d, 0, 0, width, height);
        }
    }

    private void repaint() {
        if (component != null) {
            EventQueue.invokeLater(() -> component.repaint());
        }
    }

    public AbstractSprite getSprite() {
        return sprite;
    }

    public void setSprite(AbstractSprite sprite) {
        this.sprite = sprite;

        if (sprite instanceof AnimatedSprite) {
            AnimatedSprite animatedSprite = (AnimatedSprite) sprite;

            animator.setSprite(animatedSprite);
            animator.start();

            alarm = new Alarm("SpriteIconAnimator");
            alarm.schedule(this::repaint, animatedSprite.getDelay(), animatedSprite.getDelay());
        } else if (alarm != null) {
            alarm.shutdown();
            alarm = null;
        }

    }
}
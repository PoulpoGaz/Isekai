package fr.poulpogaz.isekai.editor.ui.about;

import fr.poulpogaz.isekai.commons.concurrent.Alarm;
import fr.poulpogaz.isekai.editor.pack.PackSprites;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class BackgroundPanel extends Canvas {

    private static final int WIDTH = 512;
    private static final int HEIGHT = 240;

    private static final int CENTER_X = WIDTH / 2;

    private static final int ATMOSPHERE_RADIUS = 170;
    private static final int EARTH_RADIUS = 110;

    private static final int N_STARS = 256;
    private static final int MIN_SPEED = 8;
    private static final int MAX_SPEED = 24;

    private static final Color BACKGROUND_1 = new Color(23, 4, 50);
    private static final Color BACKGROUND_2 = new Color(12, 34, 81);
    private static final Color BACKGROUND_3 = new Color(18, 59, 147);
    private static final Color BACKGROUND_4 = new Color(0, 107, 70);
    private static final Color BACKGROUND_5 = new Color(47, 129, 54);
    private static final Color BACKGROUND_6 = new Color(73, 146, 49);
    private static final Color BACKGROUND_7 = new Color(100, 164, 44);

    private static final float[] FRACTIONS = new float[] {0, 0.75f};
    private static final Color[] COLORS = new Color[] {BACKGROUND_1, Color.WHITE};

    private static final int FPS = 10;

    private final Star[] stars = new Star[N_STARS];

    private Alarm alarm;

    public BackgroundPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void addNotify() {
        super.addNotify();

        alarm = new Alarm("Renderer");
        alarm.schedule(this::draw, 0, 1000 / FPS);

        Random r = new Random();
        for (int i = 0; i < stars.length; i++) {
            Star star = new Star();
            star.theta = r.nextDouble() * 2 * Math.PI;
            star.distance = r.nextInt(WIDTH - ATMOSPHERE_RADIUS) + ATMOSPHERE_RADIUS;
            star.size = r.nextInt(2) + 1;

            double speed = r.nextDouble();

            star.speed = Math.PI * 2 / ((MIN_SPEED + MAX_SPEED * speed) * FPS);

            /**
             * 1 -> tour en 4 * FPS     -> speed = 2 pi / (4 * FPS * (1 - val) )
             * 0.25 -> tour en 16 * FPS -> speed = 2 pi / (16 * FPS)
             */

            stars[i] = star;
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();

        if (alarm != null) {
            alarm.shutdown();
            alarm = null;
        }
    }

    private void draw() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        draw(g2d);

        g2d.dispose();
        bs.show();
    }

    private void draw(Graphics2D g2d) {
        // draw background
        g2d.setColor(BACKGROUND_1);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.setColor(BACKGROUND_2);
        fillCircle(g2d, ATMOSPHERE_RADIUS);

        g2d.setColor(BACKGROUND_3);
        fillCircle(g2d, (ATMOSPHERE_RADIUS + EARTH_RADIUS) / 2);

        g2d.setColor(BACKGROUND_4);
        fillCircle(g2d, EARTH_RADIUS);

        g2d.setColor(BACKGROUND_5);
        fillCircle(g2d, 3 * EARTH_RADIUS / 4);

        g2d.setColor(BACKGROUND_6);
        fillCircle(g2d, 2 * EARTH_RADIUS / 4);

        g2d.setColor(BACKGROUND_7);
        fillCircle(g2d, EARTH_RADIUS / 4);

        // draw sprites
        g2d.drawImage(PackSprites.getPlayer(), CENTER_X - 16, HEIGHT - EARTH_RADIUS - 28, 32, 32, null);

        // draw stars
        for (Star star : stars) {
            int x = (int) (Math.cos(star.theta) * star.distance) + CENTER_X;
            int y = (int) (Math.sin(star.theta) * -star.distance) + HEIGHT;
            int arcStart = (int) Math.toDegrees(star.theta);

            star.theta += star.speed;

            int x2 = (int) (Math.cos(star.theta) * star.distance) + CENTER_X;
            int y2 = (int) (Math.sin(star.theta) * -star.distance) + HEIGHT;
            int arcEnd = (int) Math.toDegrees(star.speed);

            int size = star.distance * 2;
            g2d.setPaint(new LinearGradientPaint(x, y, x2, y2, FRACTIONS, COLORS));
            g2d.drawArc(CENTER_X - star.distance, HEIGHT - star.distance,
                    size, size,
                    arcStart, arcEnd);

            star.theta += star.speed;
        }

        g2d.setColor(getForeground());

        Font font = g2d.getFont().deriveFont(36f);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString("Isekai Editor", 30, (HEIGHT - fm.getHeight()) / 2 + fm.getAscent());
    }

    private void fillCircle(Graphics2D g2d, int rad) {
        int size = rad * 2;
        g2d.fillOval(CENTER_X - rad, HEIGHT - rad, size, size);
    }

    private static class Star {

        public double theta;
        public int distance;
        public int size;
        public double speed;
    }
}
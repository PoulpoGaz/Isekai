package fr.poulpogaz.isekai.game.renderer;

public class Colors {

    public static final IColor WHITE = new ImmutableColor(1, 1, 1, 1);

    public static final IColor LIGHT_GRAY = new ImmutableColor(0.75f, 0.75f, 0.75f, 1);

    public static final IColor GRAY = new ImmutableColor(0.5f, 0.5f, 0.5f, 1);

    public static final IColor DARK_GRAY = new ImmutableColor(0.25f, 0.25f, 0.25f, 1);

    public static final IColor DARK = new ImmutableColor(0, 0, 0, 1);

    public static final IColor RED = new ImmutableColor(1, 0, 0, 1);

    public static final IColor PINK = new ImmutableColor(1, 25f / 40f, 25f / 40f, 1);

    public static final IColor ORANGE = new ImmutableColor(1, 40f / 51f, 0, 1);

    public static final IColor YELLOW = new ImmutableColor(1, 1, 0, 1);

    public static final IColor GREEN = new ImmutableColor(0, 1, 0, 1);

    public static final IColor MAGENTA = new ImmutableColor(1, 0, 1, 1);

    public static final IColor CYAN = new ImmutableColor(0, 1, 1, 1);

    public static final IColor BLUE = new ImmutableColor(0, 0, 1, 1);

    public static Color fromHSV(float hue, float saturation, float brightness) {
        float r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = brightness;
        } else {
            float h = (hue - (int) hue) * 6.0f;
            int i = (int) h;
            float f = h - i;
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));

            switch (i) {
                case 0 -> {
                    r = brightness;
                    g = t;
                    b = p;
                }
                case 1 -> {
                    r = q;
                    g = brightness;
                    b = p;
                }
                case 2 -> {
                    r = p;
                    g = brightness;
                    b = t;
                }
                case 3 -> {
                    r = p;
                    g = q;
                    b = brightness;
                }
                case 4 -> {
                    r = t;
                    g = p;
                    b = brightness;
                }
                case 5 -> {
                    r = brightness;
                    g = p;
                    b = q;
                }
            }
        }

        return new Color(r, g, b);
    }
}
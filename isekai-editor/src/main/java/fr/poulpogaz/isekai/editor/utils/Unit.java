package fr.poulpogaz.isekai.editor.utils;

public enum Unit {

    BYTE(1),
    KILO_BYTE(BYTE.one * 1024),
    MEGA_BYTE(KILO_BYTE.one * 1024),
    GIGA_BYTE(MEGA_BYTE.one * 1024),
    TERA_BYTE(GIGA_BYTE.one * 1024);

    private final double one;

    Unit(double one) {
        this.one = one;
    }

    public double to(Unit to, double value) {
        return one / to.one * value;
    }

    public float to(Unit to, float value) {
        return (float) (one / to.one * value);
    }

    public long to(Unit to, long value) {
        return (long) (one / to.one * value);
    }

    public int to(Unit to, int value) {
        return (int) (one / to.one * value);
    }
}
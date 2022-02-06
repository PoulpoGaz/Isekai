package fr.poulpogaz.isekai.editor.utils;

public class Bounds {

    private int minX;
    private int minY;

    private int maxX;
    private int maxY;

    public Bounds() {
    }

    public Bounds(int minX, int minY, int maxX, int maxY) {
        setMinX(minX);
        setMinY(minY);
        setMaxX(maxX);
        setMaxY(maxY);
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        if (minX > maxX) {
            this.minX = maxX;
            this.maxX = minX;
        } else {
            this.minX = minX;
        }
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        if (minY > maxX) {
            this.minY = maxY;
            this.maxY = minY;
        } else {
            this.minY = minY;
        }
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        if (maxX < minX) {
            this.maxX = minX;
            this.minX = maxX;
        } else {
            this.maxX = maxX;
        }
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        if (maxY < minY) {
            this.maxY = minY;
            this.minY = maxY;
        } else {
            this.maxY = maxY;
        }
    }

    public int getWidth() {
        return maxX - minX;
    }

    public void setWidth(int width) {
        maxX = minX + Math.abs(width);
    }

    public int getHeight() {
        return maxY - minY;
    }

    public void setHeight(int height) {
        maxY = minY + Math.abs(height);
    }
}
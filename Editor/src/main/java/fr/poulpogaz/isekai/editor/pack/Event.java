package fr.poulpogaz.isekai.editor.pack;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Event {

    public static final int INTRODUCTION = -1;
    public static final int END = Integer.MAX_VALUE;

    private int when;

    private BufferedImage image;

    private ArrayList<Text> texts;

    public Event(int when) {
        this.when = when;
    }

    public int getWhen() {
        return when;
    }

    public void setWhen(int when) {
        this.when = when;
    }
}
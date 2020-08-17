package fr.poulpogaz.isekai.editor.pack;

import java.util.SortedSet;
import java.util.TreeSet;

public class Timeline {

    private SortedSet<Event> events;

    public Timeline() {
        events = new TreeSet<>(this::compareTo);
    }

    private int compareTo(Event event, Event event1) {
        return Integer.compare(event.getWhen(), event1.getWhen());
    }
}
package fr.poulpogaz.isekai.editor.ui.importer;

import fr.poulpogaz.isekai.editor.pack.Level;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class LevelImporter extends SwingWorker<List<Level>, Integer> {

    private final SIPack pack;
    private final int[] levels;
    private final LevelImporterDialog dialog;

    public LevelImporter(SIPack pack, String range, LevelImporterDialog dialog) {
        this.pack = Objects.requireNonNull(pack);
        this.levels = getLevels(range);
        this.dialog = Objects.requireNonNull(dialog);
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            try {
                dialog.dispose(get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void process(List<Integer> chunks) {
        for (Integer chunk : chunks) {
            if (chunk < 0) {
                dialog.increase();
            } else {
                dialog.fail(chunk);
            }
        }
    }

    @Override
    protected List<Level> doInBackground() {
        List<Level> levels = Collections.synchronizedList(new ArrayList<>());
        for (int index : this.levels) {
            Level level = pack.importLevel(index);

            if (isCancelled()) {
                return null;
            }

            if (level == null) {
                publish(index);
            } else {
                levels.add(level);
                publish(-1);
            }
        }

        return levels;
    }

    protected int[] getLevels(String range) {
        if (range == null || range.isEmpty()) {
            throw new IllegalStateException("Range is null");
        }

        String[] ranges = range.split(",");

        int nColon = (int) range.chars().filter((c) -> c == ',').count();

        if (nColon + 1 != ranges.length) { // the number of ranges is the number of colon plus one
            throw new IllegalStateException("Colon problem");
        }

        Set<Integer> levels = new LinkedHashSet<>();

        for (String r : ranges) {
            if (r.indexOf('-') != r.lastIndexOf('-')) { // multiple hyphen
                throw new IllegalStateException("Duplicate hyphen");
            }

            String[] limits = r.split("-");

            if (limits.length == 1) {
                int val = Integer.parseInt(limits[0]);

                if (val < 1 || val > pack.nLevels()) {
                    throw new IndexOutOfBoundsException();
                }

                levels.add(val);
            } else if (limits.length == 2) {
                int min = Integer.parseInt(limits[0]);
                int max = Integer.parseInt(limits[1]);

                if (min < 1 || min > pack.nLevels() || max < 1 || max > pack.nLevels() || max < min) {
                    throw new IndexOutOfBoundsException();
                }

                for (; min <= max; min++) {
                    levels.add(min);
                }
            }
        }

        int[] array = new int[levels.size()];

        int i = 0;
        for (Integer v : levels) {
            array[i] = v;
            i++;
        }

        return array;
    }

    public int getNumberOfLevels() {
        return levels.length;
    }
}
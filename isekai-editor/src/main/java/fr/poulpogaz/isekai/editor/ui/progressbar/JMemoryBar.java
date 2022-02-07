package fr.poulpogaz.isekai.editor.ui.progressbar;

import com.formdev.flatlaf.FlatClientProperties;
import fr.poulpogaz.isekai.commons.Unit;
import fr.poulpogaz.isekai.commons.concurrent.Alarm;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public class JMemoryBar extends IskProgressBar {

    private Alarm alarm;

    private MemoryUsage memoryUsage;

    private boolean maximum = false;

    public JMemoryBar() {
        setMinimumSize(new Dimension(25, 25));
        setPreferredSize(new Dimension(100, 25));
        setStringPainted(true);
        putClientProperty(FlatClientProperties.PROGRESS_BAR_SQUARE, true);

        addActionListener((a) -> {
            System.gc();
        });

        StringBoundedRangeModel model = new StringBoundedRangeModel(this);

        model.setConverter((bar) -> {
            if(memoryUsage != null) {
                long value = Unit.BYTE.to(Unit.MEGA_BYTE, memoryUsage.getUsed());
                long max = Unit.BYTE.to(Unit.MEGA_BYTE, getMax());

                return value + " of " + max + "MB";
            } else {
                return "";
            }
        });

        setModel(model);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        alarm = new Alarm("Memory bar");
        alarm.schedule(this::updateValue, 0, 1000);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();

        alarm.shutdown();
        alarm = null;
    }

    private void updateValue() {
        memoryUsage = getMemoryUsage();

        setValue((int) (memoryUsage.getUsed() / (double) getMax() * 100));
    }

    private long getMax() {
        return maximum ? memoryUsage.getMax() : memoryUsage.getCommitted();
    }

    private Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    private MemoryUsage getMemoryUsage() {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    }

    public void showMaximum(boolean maximum) {
        this.maximum = maximum;
    }

    public boolean isShowingMaximum() {
        return maximum;
    }
}
package fr.poulpogaz.isekai.commons.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    /**
     * Explanation of the SuppressWarnings annotation.
     * Valid as of Java 17
     * @see java.util.concurrent.Executors.DefaultThreadFactory
     */
    @SuppressWarnings("removal")
    public NamedThreadFactory(String poolName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

        namePrefix = String.format("%s-%d", poolName, poolNumber.getAndIncrement());
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix, 0);

        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }

        return t;
    }
}
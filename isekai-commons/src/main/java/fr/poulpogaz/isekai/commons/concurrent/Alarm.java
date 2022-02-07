package fr.poulpogaz.isekai.commons.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Alarm {

    private final Object LOCK = new Object();

    private final ScheduledThreadPoolExecutor executor;
    private final List<Task> tasks;
    private TimeUnit unit;

    private boolean shutdown = false;

    public Alarm() {
        this("Alarm");
    }

    public Alarm(String name) {
        executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, new AlarmThreadFactory(name));

        tasks = Collections.synchronizedList(new ArrayList<>());
    }

    public Task schedule(Runnable runnable, long delay) {
        synchronized (LOCK) {
            if (!shutdown) {
                Task task = new Task();
                task.schedule(runnable, delay, TimeUnit.MILLISECONDS);
                tasks.add(task);

                return task;
            }

            return null;
        }
    }

    public Task schedule(Runnable runnable, long delay, long period) {
        synchronized (LOCK) {
            if (!shutdown) {
                Task task = new Task();
                task.scheduleWithFixedDelay(runnable, delay, period, TimeUnit.MILLISECONDS);
                tasks.add(task);

                return task;
            }

            return null;
        }
    }

    public Task schedule(Runnable runnable, long delay, long period, TimeUnit unit) {
        synchronized (LOCK) {
            if (!shutdown) {
                Task task = new Task();
                task.scheduleWithFixedDelay(runnable, delay, period, unit);
                tasks.add(task);

                return task;
            }

            return null;
        }
    }

    public Task scheduleAtFixedRate(Runnable runnable, long delay, long period) {
        synchronized (LOCK) {
            if (!shutdown) {
                Task task = new Task();
                task.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
                tasks.add(task);

                return task;
            }

            return null;
        }
    }

    public Task scheduleAtFixedRate(Runnable runnable, long delay, long period, TimeUnit unit) {
        synchronized (LOCK) {
            if (!shutdown) {
                Task task = new Task();
                task.scheduleAtFixedRate(runnable, delay, period, unit);
                tasks.add(task);

                return task;
            }

            return null;
        }
    }

    public void cancel(Task task) {
        synchronized (LOCK) {
            if (!shutdown) {
                task.cancel();
                tasks.remove(task);
            }
        }
    }

    public void cancel(Runnable runnable) {
        synchronized (LOCK) {

            if (!shutdown) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);

                    if (task.runnable == runnable) {
                        task.cancel();
                        tasks.remove(task);
                        break;
                    }
                }
            }
        }
    }

    public void cancelAllTasks() {
        synchronized (LOCK) {
            if (!shutdown) {
                for (Task task : tasks) {
                    task.cancel();
                }

                tasks.clear();
            }
        }
    }

    public void awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (LOCK) {
            if (!shutdown) {
                for (Task task : tasks) {

                    if (task.future != null) {
                        task.future.get(timeout, timeUnit);
                    }
                }

                tasks.clear();
            }
        }
    }

    public void shutdown() {
        synchronized (LOCK) {
            if (!shutdown) {
                cancelAllTasks();

                executor.shutdownNow();
                tasks.clear();

                shutdown = true;
            }
        }
    }

    public void setTimeUnit(TimeUnit unit) {
        synchronized (LOCK) {
            this.unit = unit;
        }
    }

    public TimeUnit getTimeUnit() {
        return unit;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int getTaskListSize() {
        return tasks.size();
    }

    public class Task {

        private ScheduledFuture<?> future;
        private Runnable runnable;

        private Task() {
        }

        public void schedule(Runnable command, long delay, TimeUnit unit) {
            synchronized (LOCK) {
                future = executor.schedule(command, delay, unit);
                runnable = command;
            }
        }

        public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            synchronized (LOCK) {
                future = executor.scheduleAtFixedRate(command, initialDelay, period,unit);
                runnable = command;
            }
        }

        public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            synchronized (LOCK) {
                future = executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
                runnable = command;
            }
        }

        public void cancel() {
            synchronized (LOCK) {
                if (future != null) {
                    future.cancel(false);
                }
            }
        }

        public long getRemainingDelay() {
            return getRemainingDelay(unit);
        }

        public long getRemainingDelay(TimeUnit unit) {
            return future.getDelay(unit);
        }
    }

    private static class AlarmThreadFactory implements ThreadFactory {

        private String name;

        public AlarmThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, name);

            t.setDaemon(true);

            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    }
}

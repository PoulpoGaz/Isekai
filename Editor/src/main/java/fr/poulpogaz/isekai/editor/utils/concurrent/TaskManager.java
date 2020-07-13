package fr.poulpogaz.isekai.editor.utils.concurrent;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TaskManager {

    private static final HashMap<String, TaskGroup> groups = new HashMap<>();

    public static void registerGroup(TaskGroup group) {
        if (groups.containsKey(group.getName())) {
            throw new IllegalStateException("This group name already exists!");
        }
        groups.put(group.getName(), group);
    }

    public static TaskGroup getGroup(String name) {
        if (groups.containsKey(name)) {
            return groups.get(name);
        } else {
            throw new IllegalStateException("Task group with name " + name + " doesn't exist");
        }
    }

    public static Future<?> execute(String name, Runnable runnable) {
        return getGroup(name).execute(runnable);
    }

    public static <T> Future<T> execute(String name, Callable<T> callable) {
        return getGroup(name).execute(callable);
    }

    public static void shutdown(String name) {
        getGroup(name).shutdown();
    }

    public static void shutdownNow(String name) {
        getGroup(name).shutdownNow();
    }

    public static TaskGroup deleteGroup(String name) {
        return groups.remove(name);
    }
}
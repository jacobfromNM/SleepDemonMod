package com.jacobfromnm.sleepdemon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;

public class ScheduledTaskManager {
    private static final List<ScheduledTaskManagerRepeatingTask> repeatingTasks = new LinkedList<>();
    private static final List<ScheduledTaskManagerScheduledTask> tasks = new LinkedList<>();

    public ScheduledTaskManager() {
    }

    public static void schedule(Runnable task, int delayTicks) {
        tasks.add(new ScheduledTaskManagerScheduledTask(task, delayTicks));
    }

    public static void scheduleRepeating(BooleanSupplier task, int intervalTicks) {
        repeatingTasks.add(new ScheduledTaskManagerRepeatingTask(task, intervalTicks));
    }

    public static void tick(MinecraftServer server) {
        List<ScheduledTaskManagerScheduledTask> toRun = new ArrayList<>();
        Iterator<ScheduledTaskManagerScheduledTask> var2 = tasks.iterator();

        ScheduledTaskManagerScheduledTask task;
        while (var2.hasNext()) {
            task = var2.next();
            task.decrement();
            if (task.isReady()) {
                toRun.add(task);
            }
        }

        tasks.removeAll(toRun);
        var2 = toRun.iterator();

        while (var2.hasNext()) {
            task = var2.next();
            task.run();
        }

        repeatingTasks.removeIf(ScheduledTaskManagerRepeatingTask::tickAndShouldRemove);
    }
}

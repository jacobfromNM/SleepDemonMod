package com.jacobfromnm.sleepdemon;

import java.util.function.BooleanSupplier;

class ScheduledTaskManagerRepeatingTask {
    private final BooleanSupplier task;
    private final int interval;
    private int ticksUntilNextRun;

    public ScheduledTaskManagerRepeatingTask(BooleanSupplier task, int interval) {
        this.task = task;
        this.interval = interval;
        this.ticksUntilNextRun = interval;
    }

    public boolean tickAndShouldRemove() {
        --this.ticksUntilNextRun;
        if (this.ticksUntilNextRun <= 0) {
            this.ticksUntilNextRun = this.interval;
            return this.task.getAsBoolean();
        } else {
            return false;
        }
    }
}

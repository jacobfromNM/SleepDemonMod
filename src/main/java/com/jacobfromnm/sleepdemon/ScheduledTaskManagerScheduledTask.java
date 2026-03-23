package com.jacobfromnm.sleepdemon;

class ScheduledTaskManagerScheduledTask {
    private final Runnable task;
    private int ticksRemaining;

    public ScheduledTaskManagerScheduledTask(Runnable task, int ticksDelay) {
        this.task = task;
        this.ticksRemaining = ticksDelay;
    }

    public void decrement() {
        --this.ticksRemaining;
    }

    public boolean isReady() {
        return this.ticksRemaining <= 0;
    }

    public void run() {
        this.task.run();
    }
}

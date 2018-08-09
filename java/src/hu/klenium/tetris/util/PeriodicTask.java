package hu.klenium.tetris.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Wrapper around Timer/TimerTask, it can be used to schedule tasks.
 *
 * It uses a new thread to wait {@code delay} ms effectively,
 * execute {@code task}, and then start the period again, untill it is stopped.
 * PeriodicTask is not started automatically, you have to start it when needed.
 * After starting a PeriodicTask, {@code task} will be executed for the first time
 * after {@code delay} ms.
 */
public class PeriodicTask {
    private Timer timer;
    private final Runnable task;
    private final int delay;
    /**
     * Prepares a new PeriodicTask, which can be started later.
     * @param task The task that need to be executed in every periods.
     * @param delay The length bettween two periods, in milliseconds.
     */
    public PeriodicTask(Runnable task, int delay) {
        this.task = task;
        this.delay = delay;
    }
    /**
     * If not running yet, starts the PeriodicTask.
     * If the timer has already been started, calling {@code start} has no effect.
     */
    public void start() {
        if (timer == null) {
            timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override public void run() {
                    task.run();
                }
            }, delay, delay);
        }
    }
    /**
     * Stops the PeriodicTask.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    /**
     * Resets the period's time, so that no matter how long had the task been last preformed,
     * next time it will be executed again after {@code delay} ms.
     */
    public void reset() {
        stop();
        start();
    }
}
package Control;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Timer {
    public static final int DURATION_INFINITY = -1;
    private volatile boolean isRunning = false;
    private final long interval;
    private long elapsedTime;
    private final long duration;
    private final ScheduledExecutorService execService = Executors
            .newSingleThreadScheduledExecutor();
    private Future<?> future = null;

    public Timer(long interval, long duration) {
        this.interval = interval;
        this.duration = duration;
        this.elapsedTime = 0;
    }

    public void start() {
        if (isRunning)
            return;

        isRunning = true;
        future = execService.scheduleWithFixedDelay(() -> {
            onTick();
            elapsedTime += Timer.this.interval;
            if (duration > 0) {
                if (elapsedTime >= duration) {
                    onFinish();
                    future.cancel(false);
                }
            }
        }, 0, this.interval, TimeUnit.MILLISECONDS);
    }

    public void pause() {
        if (!isRunning) return;
        future.cancel(false);
        isRunning = false;
    }

    public void resume() {
        this.start();
    }

    protected abstract void onTick();


    protected abstract void onFinish();

    public void cancel() {
        pause();
        this.elapsedTime = 0;
    }


    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public long getRemainingTime() {
        if (this.duration < 0) {
            return Timer.DURATION_INFINITY;
        } else {
            return duration - elapsedTime;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}

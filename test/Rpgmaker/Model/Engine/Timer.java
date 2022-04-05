package Rpgmaker.Model.Engine;

import java.awt.event.ActionListener;

public class Timer extends javax.swing.Timer {
    long startTime;
    long timePaused;
    int currentDelay;
    int nextDelay;

    public Timer(int delay, ActionListener listener) {
        super(delay, listener);
        this.startTime = -1;
        this.timePaused = -1;
        this.currentDelay = delay;
        this.nextDelay = delay;
    }

    @Override
    public void start() {
        super.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        super.stop();
        startTime = -1;
    }

    public void updateDelay(int nextDelay) {
        currentDelay = this.nextDelay;
        setDelay(nextDelay);
        this.nextDelay = nextDelay;
        timePaused = -1;
        startTime = System.currentTimeMillis();
    }

    public void pause() {
        if (startTime == -1)
            return;
        timePaused = System.currentTimeMillis();
        super.stop();
    }

    public void resume() {
        if (timePaused == -1)
            return;
        currentDelay -= timePaused - startTime;
        setInitialDelay(currentDelay);
        start();
        timePaused = -1;
    }
}

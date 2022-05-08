package Models;

import Control.Timer;

public class Chronometer extends Timer {

    private final ChronometerLabel targetLabel;

    public Chronometer(long interval, long duration, ChronometerLabel targetLabel) {
        super(interval, duration);
        this.targetLabel = targetLabel;

    }

    @Override
    protected void onTick() {
        targetLabel.updateText(super.getElapsedTime());
    }

    @Override
    protected void onFinish() {
    }
}

package Models;

import Control.Timer;

public class Chronometer extends Timer {

    public Chronometer(long interval, long duration){
        super(interval, duration);
    }
    @Override
    protected void onTick() {
    }

    @Override
    protected void onFinish() {
    }
}

package Saving;

import java.util.Objects;

public final class TimeLevel {
    private final int levelNumber;
    private final long time;

    public TimeLevel(int levelNumber, long time) {
        this.levelNumber = levelNumber;
        this.time = time;
    }

    public int levelNumber() {
        return levelNumber;
    }

    public long time() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeLevel) obj;
        return this.levelNumber == that.levelNumber &&
                this.time == that.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelNumber, time);
    }

    @Override
    public String toString() {
        return "TimeLevel[" +
                "levelNumber=" + levelNumber + ", " +
                "time=" + time + ']';
    }

}

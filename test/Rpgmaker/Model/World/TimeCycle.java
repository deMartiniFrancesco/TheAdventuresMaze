package Rpgmaker.Model.World;

public class TimeCycle {
    boolean active;
    boolean isNight;
    int nightDuration;
    int dayDuration;

    public TimeCycle() {
        active = false;
        isNight = false;
        nightDuration = 10;
        dayDuration = 10;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNightDuration() {
        return nightDuration;
    }

    public void setNightDuration(int nightDuration) {
        this.nightDuration = nightDuration;
    }

    public int getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(int dayDuration) {
        this.dayDuration = dayDuration;
    }

    public void reverseActive() {
        active = !active;
    }

    public boolean isNight() {
        return isNight;
    }

    public void switchTime() {
        isNight = ! isNight;
    }

    public int getDelay() {
        if (isNight)
            return getNightDuration();
        return getDayDuration();
    }

    public int getNextDelay() {
        if (isNight)
            return getDayDuration();
        return getNightDuration();
    }
}

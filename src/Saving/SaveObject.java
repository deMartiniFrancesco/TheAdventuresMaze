package Saving;

import java.util.List;
import java.util.Objects;

public final class SaveObject {
    private final String name;
    private final List<TimeLevel> timeLevels;

    public SaveObject(String name, List<TimeLevel> timeLevels) {
        this.name = name;
        this.timeLevels = timeLevels;
    }

    public String name() {
        return name;
    }

    public List<TimeLevel> timeLevels() {
        return timeLevels;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SaveObject) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.timeLevels, that.timeLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timeLevels);
    }

    @Override
    public String toString() {
        return "\nSaveObject{" +
                "\n\t\tname = '" + name + '\'' +
                "\n\t\ttimeLevels = " + timeLevels +
                "}\n";
    }
}

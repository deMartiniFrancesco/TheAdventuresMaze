package Saving;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SaveObject {
    private final String name;
    private final Set<TimeLevel> timeLevels;

    public SaveObject(String name, Set<TimeLevel> timeLevels) {
        this.name = name;
        this.timeLevels = timeLevels;
    }

    public String name() {
        return name;
    }

    public Set<TimeLevel> timeLevels() {
        return timeLevels;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SaveObject) obj;
        return this.name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "\nSaveObject{" +
                "\n\t\tname = '" + name + '\'' +
                "\n\t\ttimeLevels = " + timeLevels +
                "}\n";
    }
}

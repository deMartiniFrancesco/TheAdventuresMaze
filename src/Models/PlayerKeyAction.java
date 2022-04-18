package Models;

public enum PlayerKeyAction {

    TOP(0, "W"),
    RIGHT(1, "D"),
    BOTTOM(2, "S"),
    LEFT(3, "A"),

    RESET(-1, "R"),

    PAUSE(-1, "Escape");

    private final int directionIndex;
    private final String key;

    PlayerKeyAction(int index, String key) {
        directionIndex = index;
        this.key = key;
    }

    public int getDirectionIndex() {
        return directionIndex;
    }

    public String getKey() {
        return key;
    }
}

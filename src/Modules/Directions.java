package Modules;

public enum Directions {

    TOP(0),
    RIGHT(1),
    BOTTOM(2),
    LEFT(3);

    private final int directionIndex;

    Directions(int index) {
        directionIndex = index;
    }
    public int getDirectionIndex() {
        return directionIndex;
    }
}

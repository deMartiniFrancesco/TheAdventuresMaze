package Modules;

public record Coordinate(
        int coordX,
        int coordY
) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (coordX != that.coordX) return false;
        return coordY == that.coordY;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}

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

    public boolean rangeOf(Coordinate coordinate) {

        //        System.out.printf("Y = %d X = %d,   CY = %d CX = %d -> %s%n",
//                coordY,
//                coordX,
//                coordinate.coordY ,
//                coordinate.coordX,
//                isInRange);

        return Math.abs(coordX - coordinate.coordX) <= 1 && Math.abs(coordY - coordinate.coordY) <= 1;
    }
}

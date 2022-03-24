package Modules;

import com.google.common.collect.*;

import java.util.HashMap;
import java.util.UUID;

public class Grid {


    private final UUID uuid = UUID.randomUUID();

    private final HashMap<Directions, Grid> directionsReferance = new HashMap<>();
    private final Table<Integer, Integer, Object> campo = HashBasedTable.create();


    private final int lenCampo = 10;

    public Grid() {

        for (Directions direction: Directions.values()) {
            directionsReferance.put(direction, null);
        }
        initCampo();
    }

    private void initCampo() {
        for (int row = 0; row < lenCampo; row++) {
            for (int column = 0; column < lenCampo; column++) {
                campo.put(row, column, "   ");
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < lenCampo; row++) {
            for (int column = 0; column < lenCampo; column++) {
                output.append(campo.get(row, column)).append("|");
            }
            output.append("\n");
        }
        return output.toString();
    }
}

class Test{
    public static void main(String[] args) {

        Grid grid = new Grid();
        System.out.println(grid);

    }
}
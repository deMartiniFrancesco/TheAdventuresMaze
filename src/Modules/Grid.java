package Modules;

import Interfaces.Objects.ObjectInterface;
import Interfaces.Objects.SpawnabeObjectInterface;
import Modules.Player.Player;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import Main.Game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class Grid {


    private final UUID uuid = UUID.randomUUID();

    private final Game main;

    private final HashMap<Directions, Grid> directionsReferance = new HashMap<>();
    private final Table<Integer, Integer, Object> campo = HashBasedTable.create();


    private final int lenCampo = 10;

    public Grid(Game main) {
        this.main = main;
        for (Directions direction : Directions.values()) {
            directionsReferance.put(direction, null);
        }
    }

    public Table<Integer, Integer, Object> getCampo() {
        return campo;
    }

    public int getLenCampo() {
        return lenCampo;
    }

    public boolean addPlayer(Player player) {
        Coordinate coordinate = player.getCoords();
        if (isEmpty(coordinate)) {
            campo.put(coordinate.coordX(), coordinate.coordY(), player);
            return true;
        }
        return false;
    }

    private boolean isEmpty(Coordinate coordinate) {
        return campo.get(coordinate.coordX(), coordinate.coordY()) == null;
    }


    public <T extends SpawnabeObjectInterface> void generateObjectRandom(Class<T> clazz, int amount){
        for (int i = 0; i < amount; i++) {
            Coordinate newRandomCoord;

            do {
                newRandomCoord = main.getCoordsProvider().newRandomCoord(lenCampo);

            }while (!isEmpty(newRandomCoord));

            try {
                campo.put(newRandomCoord.coordX(), newRandomCoord.coordY(), clazz.
                        getDeclaredConstructor(Coordinate.class).
                        newInstance(newRandomCoord));
            }
            catch (Exception ignored){

            }

        }

    }
}



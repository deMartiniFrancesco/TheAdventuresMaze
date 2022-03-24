package Providers;

import Main.Game;
import Modules.Coordinate;

import java.util.Random;

public class CoordsProvider {





    Random random = new Random();

    public CoordsProvider(Game game) {

    }



    public Coordinate newRandomCoord(int range){
        return new Coordinate(random.nextInt(range), random.nextInt(range));
    }


    public boolean coordRangeCoord(Coordinate coord1, Coordinate coord2, int range) {

        return Math.abs(coord1.coordX() - coord2.coordX()) <= range && Math.abs(coord1.coordY() - coord2.coordY()) <= range;
    }



}

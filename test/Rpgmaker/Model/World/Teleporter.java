package Rpgmaker.Model.World;

import java.awt.*;

public class Teleporter {
    String name;
    int mapDestId;
    Point position;

    Point destPosition;

    public Teleporter(Map map, String name, Point p) {
        this.name = map.toString() + " | " + name;
        this.position = p;
        this.destPosition = null;
        this.mapDestId = -1;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setDest(Map map, Point point) {
        mapDestId = map.id;
        destPosition = new Point(point);
    }

    public int getMapDestId() {
        return mapDestId;
    }

    public Point getPosition() {
        return position;
    }

    public Point getPointDest() {
        return destPosition;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package Rpgmaker.Model.World;

import Rpgmaker.Model.Editor.EditorState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Foreground {
    Tile t;
    String name;
    public boolean isBreakable;
    public boolean isHided;
    public boolean isPickable;

    transient public boolean isRemoved;
    transient public boolean isShowed;

    private String breaker;

    public Foreground(Tile t) {
        this.t = t;
        name = t.toString();
        isBreakable = false;
        isHided = false;
        isPickable = false;
        isRemoved = false;
        isShowed = false;
        breaker = "";
    }

    public BufferedImage get() {
        return t.get();
    }

    public Tile getTile() {
        return t;
    }

    public String getTileName() {
        return t.toString();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public Point getPoint() {
        var forSet = EditorState.getInstance().mapState.currentMap.getForegroundSet();
        for(Point p : forSet.keySet()) {
            if (forSet.get(p) == this)
                return p;
        }
        return null;
    }

    public void setBreaker(String breaker) {
        this.breaker = breaker;
    }

    public String getBreaker() {
        if (breaker == null)
            breaker = "";
        return breaker;
    }

    public ImportedTile getImported() {
        return (ImportedTile)t;
    }

    public void setTile(Tile tile) {
        this.t = tile;
    }
}

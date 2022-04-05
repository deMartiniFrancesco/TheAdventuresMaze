package Rpgmaker.Model.World;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Tools.Tools;

import java.awt.geom.Point2D;
import java.util.Vector;

public class Player {
    private int mapId;
    private Point2D.Double position;
    private Animation anim;
    private Direction direction;

    transient Vector<Foreground> items = new Vector<>();

    public Player() {
        position = new Point2D.Double(0,0);
        direction = Direction.DOWN;
        anim = null;
    }

    public Player(Player player) {
        this.mapId = player.mapId;
        this.position = (Point2D.Double)player.position.clone();
        this.anim = player.anim;
        this.direction = player.direction;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public int getMapId() {
        return mapId;
    }

    public void setPosition(Point2D.Double point, int map) {
        position = point;
        this.mapId = map;
    }

    public void setAnim() {
        if (EditorState.getInstance().tilesState.currentTile instanceof Animation)
            anim = (Animation)EditorState.getInstance().tilesState.currentTile;
    }
    public void setAnim(Animation anim) {
        this.anim = anim;
    }

    public Animation getAnim() {
        return anim;
    }

    public void move(Direction dir, int delta_time) {
        this.direction = dir;
        switch (dir) {
            case DOWN:
                Tools.translate2D(position, 0, 0.01 * delta_time);
                break;
            case LEFT:
                Tools.translate2D(position,-0.01 * delta_time, 0);
                break;
            case RIGHT:
                Tools.translate2D(position,0.01 * delta_time, 0);
                break;
            case UP:
                Tools.translate2D(position,0, -0.01 * delta_time);
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector<Foreground> getItems() {
        return items;
    }

    public boolean hasItem(String breaker) {
        for (Foreground f : items) {
            if (f.getTileName().equals(breaker))
                return true;
        }
        return false;
    }

    public void setDirection(Direction playerDirection) {
        direction = playerDirection;
    }
}

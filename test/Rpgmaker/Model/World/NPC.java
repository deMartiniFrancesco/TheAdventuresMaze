package Rpgmaker.Model.World;

import Rpgmaker.Tools.Tools;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class NPC {
    private String name;
    private String message;
    private Direction direction;
    transient private Point2D.Double dest;
    private Animation anim;
    private Point2D.Double coordinates;
    private boolean isMoving;

    public Vector<Point> revealForeground = new Vector<>();

    public NPC(Animation anim, Point coordinates) {
        this.anim = anim;
        this.dest = null;
        this.message = "Hello!";
        this.coordinates = new Point2D.Double(coordinates.x, coordinates.y);
        this.direction = Direction.DOWN;
        this.isMoving = false;
    }

    @Override
    public String toString() {
        if (name == null)
            return anim.toString();
        return name;
    }

    public Animation getAnimation() {
        return anim;
    }

    public String getName() {
        if (name == null)
            return anim.getName();
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Point2D.Double getPoint() {
        return this.coordinates;
    }

    public Point getIntPoint() {
        return new Point((int)coordinates.x, (int)coordinates.y);
    }

    public void setAnimation(Animation animation) {
        anim = animation;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
        System.out.println(moving);
    }

    public void move(Direction dir, int delta_time) {
        this.direction = dir;
        double translation = 0.003 * delta_time;
        switch (dir) {
            case DOWN:
                if (dest.y - coordinates.y < translation)
                    coordinates.y = dest.y;
                else
                    Tools.translate2D(coordinates, 0, translation);
                break;
            case LEFT:
                if (coordinates.x - dest.x < translation)
                    coordinates.x = dest.x;
                else
                    Tools.translate2D(coordinates,-1 * translation, 0);
                break;
            case RIGHT:
                if (dest.x - coordinates.x < translation)
                    coordinates.x = dest.x;
                else
                    Tools.translate2D(coordinates,translation, 0);
                break;
            case UP:
                if (coordinates.y - dest.y < translation)
                    coordinates.y = dest.y;
                else
                    Tools.translate2D(coordinates,0, -1 * translation);
                break;
        }
    }

    public boolean moveNPC(int delta_time, Map map) {
        if (dest == null) {
            do {
                int deltaX = getMoveDelta();
                int deltaY = getMoveDelta();
                dest = new Point2D.Double(coordinates.x + deltaX, coordinates.y + deltaY);

            } while (map.checkBoundsNPC(this, (int) dest.x, (int) dest.y));
        }

        Direction dir = Direction.UP;
        if (dest.x - coordinates.x < 0) {
            dir = Direction.LEFT;
        } else if (dest.x - coordinates.x > 0) {
            dir = Direction.RIGHT;
        } else if (dest.y - coordinates.y < 0) {
            dir = Direction.UP;
        } else if (dest.y - coordinates.y > 0) {
            dir = Direction.DOWN;
        } else {
            dest = null;
            return false;
        }
        if (!map.checkBoundsNPC(this, dir, coordinates, delta_time)) {
            this.move(dir, delta_time);
            return true;
        }
        return false;
    }

    private int getMoveDelta() {
        double res = 3 * Math.random();
        if (res < 1.0)
            return -1;
        else if (res < 2)
            return 0;
        return 1;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setName(String s) {
        this.name = s;
    }

    public Vector<Point> getRevealForeground() {
        if (revealForeground == null)
            revealForeground = new Vector<>();
        return revealForeground;
    }

    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    public void setDest(Point2D.Double dest) {
        this.dest = dest;
    }
}

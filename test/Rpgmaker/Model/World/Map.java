package Rpgmaker.Model.World;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.MapState;
import Rpgmaker.Model.Editor.TileType;
import Rpgmaker.Tools.ActionManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class Map {
    public int id;
    String name;

    Dimension dimension;
    Vector<Tile> background;
    Vector<Boolean> walkable;
    HashMap<Point, Foreground> foreground; // Tile but it's a ImportedTile. Point is top left corner.
    Vector<NPC> npc;
    Vector<Teleporter> teleporters;
    Music music;
    Tile backgroundTile;

    public Map(Dimension dimension, String name) {
        this.name = name;
        this.dimension = dimension;
        this.background = new Vector<>(dimension.width * dimension.height);
        this.walkable = new Vector<>(dimension.width * dimension.height);
        this.foreground = new HashMap<>();
        this.npc = new Vector<>();
        this.teleporters = new Vector<>();
        for (int i = 0; i < dimension.width * dimension.height; i++) {
            background.add(i, Tile.getTransPlaceholder());
            walkable.add(true);
        }
        this.backgroundTile = Tile.getPlaceholder();
    }

    public void setBackgroundTile(Tile backgroundTile) {
        this.backgroundTile = backgroundTile;
    }

    public Map(Map map) {
        this.id = map.id;
        this.name = map.name;
        this.dimension = new Dimension(map.dimension);
        this.background = new Vector<>(map.background);
        this.foreground = new HashMap<>(map.foreground);
        this.teleporters = new Vector<>(map.teleporters);
        this.walkable = new Vector<>(map.walkable);
        this.backgroundTile = map.backgroundTile;
        this.npc = new Vector<>(map.npc);
        Music music = map.getMusic();
        if (music != null) {
            try {
                this.music = music;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void draw(Tile currentTile, Point in, Point out) {
        if (currentTile.getType() == TileType.NPC) {
            setNpc(currentTile, in);
            return;
        }
        ActionManager.saveAction(new Map(this));
        if (out == null)
            out = in;
        if (in != null) {
            if (currentTile instanceof BigTile) {
                drawBigTile((BigTile)currentTile, in, out);
                return;
            }
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (checkBounds(x, y))
                        continue;
                    if (currentTile instanceof ImportedTile) {
                        Point isOccupied = isOccupied(x, y, ((ImportedTile) currentTile).getDimention());
                        if (currentTile.getName().equals("eraser.png")) {
                            if (isOccupied != null)
                                removeFore(isOccupied);
                            continue;
                        }
                        if (isOccupied != null) {
                            continue;
                        }
                        setFore(x, y, (ImportedTile) currentTile);
                    } else {
                        if (currentTile.getName().equals("eraser.png"))
                            setTile(x, y, Tile.getTransPlaceholder());
                        else
                            setTile(x, y, currentTile);
                    }
                }
            }
        }
    }

    private void setNpc(Tile currentTile, Point in) {
        if (currentTile.getName().equals("eraser.png")) {
            NPC toDelete = null;
            for (NPC thisNpc : npc) {
                if (thisNpc != null && thisNpc.getPoint().equals(in)) {
                    toDelete = thisNpc;
                    break;
                }
            }
            if (toDelete != null) {
                npc.remove(toDelete);
                MapState mapState = EditorState.getInstance().mapState;
                mapState.updateRequestOut = new Point(in.x + toDelete.getAnimation().getSize(),
                        in.y + toDelete.getAnimation().getSize());
            }
        } else {
            npc.add(new NPC((Animation)currentTile, in));
        }
    }

    private void drawBigTile(BigTile bt, Point in, Point out) {
        if (in.equals(out) && bt.cur == -1)
            out = new Point(out.x + bt.getWidth() - 1, out.y + bt.getHeight() - 1);
        for (int x = in.x; x <= out.x; x++) {
            for (int y = in.y; y <= out.y; y++) {
                if (bt.cur == -1) {
                    if (bt.getWidth() == 3 && bt.getHeight() == 3) {
                        setTile(x, y, bt.getTile(1, 1));
                    } else {
                        setTile(x, y, bt.getTile(x % bt.getWidth(), y % bt.getHeight()));
                    }
                } else {
                    setTile(x, y, bt.getTile(bt.cur));
                }
            }
        }
        if (bt.cur == -1 && bt.getWidth() == 3 && bt.getHeight() == 3) {
            for (int x = in.x + 1; x < out.x; x++) {
                setTile(x, in.y, bt.getTile(1,0));
                setTile(x, out.y, bt.getTile(1,2));
            }
            for (int y = in.y + 1; y < out.y; y++) {
                setTile(in.x, y, bt.getTile(0,1));
                setTile(out.x, y, bt.getTile(2,1));
            }
            setTile(in.x, in.y, bt.getTile(0, 0));
            setTile(in.x, out.y, bt.getTile(0, 2));
            setTile(out.x, in.y, bt.getTile(2, 0));
            setTile(out.x, out.y, bt.getTile(2, 2));
        }
    }

    public Point isOccupied(int x, int y, Dimension dimension) {
        for (int i = x; i < x + dimension.width; i++) {
            for (int j = y; j < y + dimension.height; j++) {
                for (var item : foreground.entrySet()) {
                    ImportedTile tile = (ImportedTile)item.getValue().getTile();
                    Point pt = item.getKey();
                    if (i >= pt.x && i < pt.x + tile.getWidth() && j >= pt.y && j < pt.y + tile.getHeight()) {
                        return pt;
                    }
                }
            }
        }
        return null;
    }
    public NPC isOccupiedNPC(int x, int y, Dimension dimension) {
        for (int i = x; i < x + dimension.width; i++) {
            for (int j = y; j < y + dimension.height; j++) {
                for (var item : npc) {
                    Animation tile = item.getAnimation();
                    Point pt = item.getIntPoint();
                    if (i >= pt.x && i < pt.x + tile.getSize() && j >= pt.y && j < pt.y + tile.getSize()) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public boolean checkBoundsPerso(Player perso, Direction dir, Point2D p, int delta_time) {
        switch (dir) {
            case DOWN:
                return checkBoundsPerso(perso, p.getX(), p.getY() + 0.01 * delta_time);
            case LEFT:
                return checkBoundsPerso(perso, p.getX() - 0.01 * delta_time, p.getY());
            case RIGHT:
                return checkBoundsPerso(perso, p.getX() + 0.01 * delta_time, p.getY());
            case UP:
                return checkBoundsPerso(perso, p.getX(), p.getY() - 0.01 * delta_time);
        }
        return false;
    }

    private boolean checkBoundsPerso(Player perso, double x, double y) {
        int size = perso.getAnim().getSize();
        return checkBoundsAnim(x, y, size);
    }

    public boolean checkBoundsNPC(NPC npc, double x, double y) {
        int size = npc.getAnimation().getSize();
        return checkBoundsAnim(x, y, size);
    }

    private boolean checkBoundsAnim(double x, double y, int size) {
        int dy = size / 2;
        for (double iy = y + dy; iy < y + size; iy++) {
            for (double ix = x + 0; ix < x + size; ix++) {
                int intx = (int) Math.round(ix);
                int inty = (int) Math.round(iy);
                boolean res = checkBounds(intx, inty);
                if (res)
                    return true;
                boolean walkable = getWalkable(intx, inty);
                if (!walkable)
                    return true;
            }
        }
        return false;
    }

    public boolean checkBoundsNPC(NPC npc, Direction dir, Point2D p, int delta_time) {
        switch (dir) {
            case DOWN:
                return checkBoundsNPC(npc, p.getX(), p.getY() + 0.01 * delta_time);
            case LEFT:
                return checkBoundsNPC(npc, p.getX() - 0.01 * delta_time, p.getY());
            case RIGHT:
                return checkBoundsNPC(npc, p.getX() + 0.01 * delta_time, p.getY());
            case UP:
                return checkBoundsNPC(npc, p.getX(), p.getY() - 0.01 * delta_time);
        }
        return false;
    }

    public boolean checkBounds(int x, int y) {
        if (x < 0 || y < 0)
            return true;
        if (x >= dimension.width || y >= dimension.height)
            return true;
        return false;
    }

    public Tile getTile(int x, int y) {
        if (checkBounds(x, y))
            return Tile.getTransPlaceholder();
        return background.get(x + dimension.width * y);
    }
    public void setTile(int x, int y, Tile tile) {
        if (checkBounds(x, y))
            return;
        background.set(x + dimension.width * y, tile);
        if (isOccupied(x, y, new Dimension(1, 1)) == null)
            setWalkable(x, y, tile.defaultWalkable);
    }

    public void setFore(int x, int y, ImportedTile tile) {
        if (checkBounds(x, y))
            return;

        foreground.put(new Point(x, y), new Foreground(tile));

        for (int i = x; i < x + tile.getWidth(); i++) {
            for (int j = y; j < y + tile.getHeight(); j++) {
                setWalkable(i, j, tile.defaultWalkable);
            }
        }
    }
    private void removeFore(Point pt) {
        if (checkBounds(pt.x, pt.y))
            return;
        ImportedTile imp = (ImportedTile)foreground.get(pt).getTile();
        Dimension dim = new Dimension(imp.getWidth(), imp.getHeight());

        extendRequest(pt);
        foreground.remove(pt);

        for (int i = pt.x; i < pt.x + dim.getWidth(); i++) {
            for (int j = pt.y; j < pt.y + dim.getHeight(); j++) {
                setWalkable(i, j, getTile(i, j).defaultWalkable);
            }
        }
    }

    private void extendTileOccupied(int x, int y) {
        Point isOccupied = isOccupied(x, y, new Dimension(1, 1));
        if (isOccupied != null)
            extendRequest(isOccupied.getLocation());
    }

    private void extendRequest(Point pt) {
        ImportedTile imp = (ImportedTile)foreground.get(pt).getTile();
        MapState mapState = EditorState.getInstance().mapState;
        mapState.updateRequestIn = new Point(Math.min(mapState.updateRequestIn.x, pt.x),
                Math.min(mapState.updateRequestIn.y, pt.y));
        mapState.updateRequestOut = new Point(Math.max(mapState.updateRequestOut.x, pt.x + imp.getWidth()),
                Math.max(mapState.updateRequestOut.y, pt.y + imp.getHeight()));
    }

    public boolean getWalkable(int x, int y) {
        if (checkBounds(x, y))
            return false;
        return walkable.get(x + dimension.width * y);
    }

    public void setWalkable(int x, int y, boolean bool) {
        if (checkBounds(x, y))
            return;
        walkable.set(x + dimension.width * y, bool);
    }

    public HashMap<Point, Foreground> getForegroundSet() {
        return foreground;
    }
    public Vector<NPC> getNpcs() {
        return npc;
    }

    public Dimension getDim() {
        return dimension;
    }

    public Teleporter addTeleporter(Point point) {
        if (checkBounds(point.x, point.y))
            return null;
        Teleporter res = new Teleporter(this, String.valueOf(teleporters.size()), point);
        teleporters.add(res);
        return res;
    }

    public Vector<Teleporter> getTeleporters() {
        return this.teleporters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile getBackgroundTile() {
        return backgroundTile;
    }

    public Vector<Tile> getBackground() {
        return background;
    }

    public void remove(Point point) {
        foreground.remove(point);
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        if (music == null)
            this.music = null;
        else {
            try {
                this.music = music;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

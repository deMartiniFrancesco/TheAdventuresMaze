package Rpgmaker.Tools;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.World.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

public class Draw {

    static BufferedImage teleporter = null;

    public static synchronized void drawBackTiles(Graphics2D g, Map map, int multiply) {
        drawBackTiles(g, map, multiply, new Point(0,0), new Point(map.getDim().width - 1, map.getDim().height - 1));
    }
    public static synchronized void drawBackTiles(Graphics2D g, Map map, int multiply, Point reqIn, Point reqOut) {
        if (reqIn == null) {
            drawBackTiles(g, map, multiply);
            return;
        }
        for (int x = reqIn.x; x <= reqOut.x; x++) {
            for (int y = reqIn.y; y <= reqOut.y; y++) {
                BufferedImage tile = map.getTile(x, y).get();
                g.drawImage(tile, x * multiply, y * multiply, null);
            }
        }
    }

    public static synchronized void drawForeTiles(Graphics2D g, Map map, int multiply, boolean showHided) {
        drawForeTiles(g, map, multiply, new Point(0,0), new Point(map.getDim().width - 1, map.getDim().height - 1), showHided);
    }
    public static synchronized void drawForeTiles(Graphics2D g, Map map, int multiply, Point reqIn, Point reqOut, boolean showHided) {
        if (reqIn == null) {
            drawForeTiles(g, map, multiply, showHided);
            return;
        }
        for (int x = reqIn.x; x <= reqOut.x; x++) {
            for (int y = reqIn.y; y <= reqOut.y; y++) {
                Point pt = map.isOccupied(x, y, new Dimension(1, 1));
                if (pt != null) {
                    Foreground f = map.getForegroundSet().get(pt);
                    if (!f.isRemoved && (showHided || !f.isHided || f.isShowed)) {
                        ImportedTile tile = (ImportedTile) f.getTile();
                        drawImported(g, tile, pt, multiply);
                    }
                }
            }
        }
    }

    public static synchronized void drawTeleporters(Graphics2D g, Map map, int multiply, Point reqIn, Point reqOut) {
        if (teleporter == null) {
            try {
                teleporter = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gt = teleporter.createGraphics();
                gt.setColor(new Color(255, 50, 50, 100));
                gt.fillRect(0, 0, 16, 16);
                gt.drawImage(ImageIO.read(ClassLoader.getSystemResource("teleporter_icon.png")), 0, 0, null);
                gt.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Teleporter t : map.getTeleporters()) {
            Point p = t.getPosition();
            if ((reqIn == null && reqOut == null) ||
                 p.x >= reqIn.x && p.x <= reqOut.x && p.y >= reqIn.y && p.y <= reqOut.y) {
                g.drawImage(teleporter, p.x * multiply, p.y * multiply, null);
            }
        }
    }

    public static synchronized void drawNPC(Graphics2D g, Map map, int multiply) {
        drawNPC(g, map, multiply, new Point(0,0), new Point(map.getDim().width - 1, map.getDim().height - 1));
    }
    public static void drawNPC(Graphics2D g, Map map, int multiply, Point reqIn, Point reqOut) {
        if (reqIn == null) {
            drawNPC(g, map, multiply);
            return;
        }
        Vector<NPC> npcV = map.getNpcs();
        for (int x = reqIn.x; x <= reqOut.x; x++) {
            for (int y = reqIn.y; y <= reqOut.y; y++) {
                NPC npc = map.isOccupiedNPC(x, y, new Dimension(1, 1));
                if (npc != null) {
                    Animation tile = npc.getAnimation();
                    if (npc.isMoving())
                        drawImported(g, tile.toImportedTile(npc.getDirection()), npc.getPoint(), multiply);
                    else
                        drawImported(g, tile.toImportedTile(), npc.getPoint(), multiply);
                }
            }
        }
    }

    public static void drawPlayer(Graphics2D g, Player player, int multiply, Point reqIn, Point reqOut) {
        Point2D p = player.getPosition();
        Animation a = player.getAnim();
        if (p != null && a != null && player.getMapId() == EditorState.getInstance().mapState.currentMap.id &&
                ((reqIn == null && reqOut == null) ||
                 (p.getX() >= reqIn.x && p.getX() <= reqOut.x && p.getY() >= reqIn.y && p.getY() <= reqOut.y))) {
            g.drawImage(a.get(),(int) p.getX() * multiply, (int) p.getY() * multiply, null);
        }
    }

    public static synchronized void drawImported(Graphics2D g, ImportedTile img, Point2D top, int multiply) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                g.drawImage(img.getTile(i, j).get(), (int)((i + top.getX()) * multiply), (int)((j + top.getY()) * multiply), null);
            }
        }
    }

    public synchronized static void drawBackground(Graphics2D g, Tile tile, Dimension dimension, int multiply) {
        Dimension d = dimension;
        Point init = new Point(0, 0);
        
        BigTile bt = null;
        if (tile instanceof BigTile) {
            bt = (BigTile)tile;
            if (bt.getHeight() == 3 && bt.getWidth() == 3) {
                tile = bt.getTile(1, 1);
                d = new Dimension(d.width - 1, d.height - 1);
                init = new Point(1, 1);
            }
        }
        
        for (int x = init.x; x < d.width; x++) {
            for (int y = init.y; y < d.height; y++) {
                if (tile instanceof BigTile && (bt.getWidth() != 3 || bt.getHeight() != 3)) {
                    Tile t = bt.getTile(x % bt.getWidth(), y % bt.getHeight());
                    g.drawImage(t.get(), x * multiply, y * multiply, null);
                } else {
                    BufferedImage b = tile.get();
                    g.drawImage(b, x * multiply, y * multiply, null);
                }
            }
        }
        
        if (bt != null && bt.getWidth() == 3 && bt.getHeight() == 3) {
            d = dimension;
            for (int x = 1; x < d.width - 1; x++) {
                g.drawImage(bt.getTile(1,0).get(), x * multiply, 0, null);
                g.drawImage(bt.getTile(1,2).get(), x * multiply, (d.height - 1)* multiply, null);
            }
            for (int y = 1; y < d.height - 1; y++) {
                g.drawImage(bt.getTile(0,1).get(), 0, y * multiply, null);
                g.drawImage(bt.getTile(2,1).get(), (d.width - 1) * multiply, y * multiply, null);
            }
            g.drawImage(bt.getTile(0, 0).get(), 0, 0, null);
            g.drawImage(bt.getTile(0, 2).get(), 0, (d.height - 1) * multiply, null);
            g.drawImage(bt.getTile(2, 0).get(), (d.width - 1) * multiply, 0, null);
            g.drawImage(bt.getTile(2, 2).get(), (d.width - 1) * multiply, (d.height - 1) * multiply, null);
        }
    }

    public static boolean checkBounds(Point pt, Point in, Point out) {
        if (pt.x < in.x || pt.x > out.x)
            return false;
        if (pt.y  < in.y || pt.y > out.y)
            return false;
        return true;
    }
}

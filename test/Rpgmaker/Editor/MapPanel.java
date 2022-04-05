package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.ToolsEnum;
import Rpgmaker.Model.World.*;
import Rpgmaker.Model.Editor.MapState;
import Rpgmaker.Tools.Draw;

import javax.swing.*;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MapPanel extends JLayeredPane implements Observer {
    static Integer BACK_LAYER = 0;
    static Integer MAP_LAYER = 1;
    static Integer MID_LAYER = 2;
    static Integer SELECT_LAYER = 3;

    JLabel backLayer;
    JLabel mapLayer;
    JLabel midLayer;
    JLabel selectLayer;

    static int multiply;
    public double currentZoom = 1.;

    BufferedImage backImage = Tile.getPlaceholder().get();
    BufferedImage mapImage = Tile.getPlaceholder().get();
    BufferedImage midImage = Tile.getTransPlaceholder().get();
    BufferedImage selection_layout = Tile.getTransPlaceholder().get();

    public MapPanel() {
        backLayer = new JLabel();
        this.add(backLayer, BACK_LAYER);

        mapLayer = new JLabel();
        this.add(mapLayer, MAP_LAYER);

        midLayer = new JLabel();
        this.add(midLayer, MID_LAYER);

        selectLayer = new JLabel();
        this.add(selectLayer, SELECT_LAYER);
    }

    public synchronized void drawMap(Map map, boolean newMap) {
        if (newMap)
            mapImage = createImage(map.getDim());

        MapState mapState = EditorState.getInstance().mapState;
        if (mapState.updateRequestIn != null) {
            clearRectBi(mapImage, mapState.updateRequestIn, mapState.updateRequestOut);
        }

        Graphics2D g = mapImage.createGraphics();
        Draw.drawBackTiles(g, map, multiply, mapState.updateRequestIn, mapState.updateRequestOut);
        Draw.drawForeTiles(g, map, multiply, mapState.updateRequestIn, mapState.updateRequestOut, true);
        Draw.drawNPC(g, map, multiply, mapState.updateRequestIn, mapState.updateRequestOut);
        Draw.drawPlayer(g, EditorState.getInstance().world.getPlayer(), multiply, mapState.updateRequestIn, mapState.updateRequestOut);
        Draw.drawTeleporters(g, map, multiply, mapState.updateRequestIn, mapState.updateRequestOut);
        g.dispose();

        if (newMap) {
            mapLayer.setIcon(new ImageIcon(mapImage));
            mapLayer.setBounds(0, 0, mapImage.getWidth(), mapImage.getHeight());

            this.setSizeMap();
        }
        repainRect(this, mapState);
    }



    public synchronized void show_walkable(MapState mapState, boolean newMap) {
        if (newMap)
            midImage = createImage(mapState.currentMap.getDim());

        Graphics2D g = midImage.createGraphics();
        for (int x = 0; x < mapState.currentMap.getDim().width; x++) {
            for (int y = 0; y < mapState.currentMap.getDim().height; y++) {
                if (mapState.currentMap.getWalkable(x, y))
                    g.setColor(new Color(0, 200, 0, 100));
                else
                    g.setColor(new Color(200, 0, 0, 100));

                g.drawRect(x * multiply, y * multiply, 16, 16);
            }
        }
        g.dispose();

        if (newMap) {
            midLayer.setIcon(new ImageIcon(midImage));
            midLayer.setBounds(0, 0, midImage.getWidth(), midImage.getHeight());
        }
        this.repaint();
    }

    private void drawBack(MapState mapState, boolean newMap) {
        if (newMap)
            backImage = createImage(mapState.currentMap.getDim());

        Graphics2D g = backImage.createGraphics();
        Draw.drawBackground(g, mapState.currentMap.getBackgroundTile(), mapState.currentMap.getDim(), multiply);
        g.dispose();

        if (newMap) {
            backLayer.setIcon(new ImageIcon(backImage));
            backLayer.setBounds(0, 0, backImage.getWidth(), backImage.getHeight());
        }
        this.repaint();
    }

    public synchronized void show_selection(MapState mapState) {
        Point in = mapState.selectionIn;
        Point out = mapState.selectionOut;
        Tile cur = null;

        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            cur = EditorState.getInstance().tilesState.currentTile;
            if (cur instanceof BigTile) {
                BigTile bt = (BigTile)cur;
                if (bt.cur != -1)
                    cur = bt.getTile(bt.cur);
            }
            if (in == null && mapState.mousePos != null) {
                in = mapState.mousePos;
                out = mapState.mousePos;
            }
        }

        if (in == null && out == null) {
            selectLayer.setIcon(null);
            selectLayer.repaint();
            return;
        }

        int sizeX = cur == null || in != out ? 1 : cur.get().getWidth() / 16;
        int sizeY = cur == null || in != out ? 1 : cur.get().getHeight() / 16;
        selection_layout = new BufferedImage((Math.abs(in.x - out.x) + 1) * multiply * sizeX,
                                            (Math.abs(in.y - out.y) + 1) * multiply * sizeY,
                                                    BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selection_layout.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        for (int x = 0; x <= selection_layout.getWidth() / 16; x += sizeX) {
            for (int y = 0; y <= selection_layout.getHeight() / 16; y += sizeY) {
                if (cur != null && ((cur.get().getWidth() == 16 && cur.get().getHeight() == 16) || in.equals(out))) {
                    g.drawImage(cur.get(), x * multiply, y * multiply, null);
                } else {
                    g.setColor(new Color(200, 0, 0, 100));
                    g.drawRect(x * multiply, y * multiply, 16, 16);
                    g.fillRect(x * multiply, y * multiply, 16, 16);
                }
            }
        }
        g.dispose();
        selectLayer.setIcon(new ImageIcon(selection_layout));
        selectLayer.setBounds(in.x * multiply, in.y * multiply,
                                  selection_layout.getWidth(), selection_layout.getHeight());

        this.repaint(in.x * multiply, in.y * multiply,
                selection_layout.getWidth(), selection_layout.getHeight());
    }

    public static BufferedImage createImage(Dimension dim) {
        BufferedImage bi = new BufferedImage(dim.width * multiply, dim.height * multiply, BufferedImage.TYPE_INT_ARGB);
        Color transparent = new Color(255, 255, 255, 255);
        return bi;
    }

    public static void clearRectBi(BufferedImage bi, Point in, Point out) {
        Graphics2D g = bi.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 255, 0, 0));
        g.fillRect(in.x * multiply, in.y * multiply, (out.x - in.x + 1) * multiply, (out.y - in.y + 1) * multiply);
        g.dispose();
    }

    public static void repainRect(MapPanel instance, MapState mapState) {
        if (mapState.updateRequestIn == null)
            instance.repaint();
        else
            instance.repaint(mapState.updateRequestIn.x * multiply,
                    mapState.updateRequestIn.y * multiply,
                    (mapState.updateRequestOut.x - mapState.updateRequestIn.x + 1) * multiply,
                    (mapState.updateRequestOut.y - mapState.updateRequestIn.y + 1) * multiply);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.scale(currentZoom, currentZoom);
        g2.transform(at);
    }

    public void addMouseCompleteListener(MouseAdapter listener) {
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    @Override
    public synchronized void update(Observable observable, Object o) {
        boolean showGrid = EditorState.getInstance().showGrid;
        boolean showWalk = EditorState.getInstance().showWalk;
        multiply = showGrid ? 17 : 16;

        if (o instanceof String && observable instanceof MapState){
            MapState mapState = (MapState) observable;

            String arg = (String) o;
            if (arg.equals("mouseOver")) {
                this.show_selection(mapState);
            }
            if (arg.equals("mousePreview")) {
                this.show_selection(mapState);
            }
            if (arg.equals("Show Walk")) {
                this.midLayer.setVisible(true);
                this.show_walkable(mapState, true);
            }
            if (arg.equals("Hide Walk")) {
                midLayer.setVisible(false);
            }
            if (arg.equals("Load Me")) {
                if (showWalk)
                    show_walkable(mapState, true);
                System.out.println("Display Map");
                drawBack(mapState, true);
                drawMap(mapState.currentMap, true);
            }
            if (arg.equals("Update Me")) {
                if (showWalk)
                    show_walkable(mapState, false);
                System.out.println("Update map");
                drawMap(mapState.currentMap, false);
            }
            if (arg.equals("Update Background")) {
                drawBack(mapState, false);
            }
            if (arg.equals("Zoom Update")) {
                currentZoom = mapState.zoomPercent;
                setSizeMap();
                this.repaint();
            }
        }
    }

    private void setSizeMap() {
        this.setPreferredSize(new Dimension((int)(mapImage.getWidth() * currentZoom), (int)(mapImage.getHeight() * currentZoom)));
        this.setSize(new Dimension((int)(mapImage.getWidth() * currentZoom), (int)(mapImage.getHeight() * currentZoom)));

        Editor.validateAll(this);
    }
}

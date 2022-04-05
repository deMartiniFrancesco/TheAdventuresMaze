package Rpgmaker.Engine;

import Rpgmaker.Model.World.ImportedTile;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.Player;
import Rpgmaker.Tools.Draw;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Display extends JLayeredPane implements Observer {
    static Integer BACK_LAYER = 0;
    static Integer FORE_LAYER = 1;
    static Integer NPC_LAYER = 2;
    static Integer PLAYER_LAYER = 3;
    static Integer TIME_CYCLE_LAYER = 4;
    static Integer INVENTORY_LAYER = 5;
    static Integer DIALOG_LAYER = 6;
    static Integer PAUSE_LAYER = 7;

    JLabel backLayer;
    JLabel foreLayer;
    JLabel npcLayer;
    JLabel playerLayer;
    JLabel timeCycleLayer;
    JLabel pauseLayer;
    JLabel dialogLayer;
    JLabel inventoryLayer;

    PauseMenu pause;
    Dialog dialog;
    Inventory inventory;

    BufferedImage background = null;
    BufferedImage foreground = null;
    BufferedImage timeCycleImage = null;
    BufferedImage npcImage = null;

    public static BufferedImage createImage(Dimension dim) {
        return new BufferedImage(dim.width * 16, dim.height * 16, BufferedImage.TYPE_INT_ARGB);
    }

    public Display() {
        backLayer = new JLabel();
        this.add(backLayer, BACK_LAYER);

        foreLayer = new JLabel();
        this.add(foreLayer, FORE_LAYER);

        npcLayer = new JLabel();
        this.add(npcLayer, NPC_LAYER);

        playerLayer = new JLabel();
        this.add(playerLayer, PLAYER_LAYER);

        timeCycleLayer = new JLabel();
        this.add(timeCycleLayer, TIME_CYCLE_LAYER);

        dialogLayer = new JLabel();
        dialogLayer.setLayout(new BorderLayout());
        dialog = new Dialog();
        dialog.setVisible(true);
        dialog.setSize(100, 100);
        dialogLayer.add(dialog, BorderLayout.PAGE_END);
        dialogLayer.setVisible(false);
        this.add(dialogLayer, DIALOG_LAYER);

        pauseLayer = new JLabel();
        pauseLayer.setLayout(new GridBagLayout());
        pause = new PauseMenu();
        pause.setVisible(true);
        pause.setSize( 100, 200);
        pauseLayer.add(pause);
        pauseLayer.setVisible(false);
        this.add(pauseLayer, PAUSE_LAYER);

        inventoryLayer = new JLabel();
        inventoryLayer.setLayout(new GridBagLayout());
        inventory = new Inventory();
        inventory.setVisible(true);
        //inventory.setSize();
        inventoryLayer.add(inventory);
        inventoryLayer.setVisible(false);
        this.add(inventoryLayer, INVENTORY_LAYER);
    }

    public void drawBackLayer(Map map) {
        Dimension dim = map.getDim();
        background = createImage(map.getDim());

        Graphics2D g = background.createGraphics();
        Draw.drawBackground(g, map.getBackgroundTile(), dim, 16);
        Draw.drawBackTiles(g, map, 16);
        g.dispose();

        BufferedImage resized = getResizedImage(background);

        backLayer.setIcon(new ImageIcon(resized));
        backLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawBackLayerUpdate() {
        if (background == null)
            return;
        BufferedImage resized = getResizedImage(background);

        backLayer.setIcon(new ImageIcon(resized));
        backLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawForeLayer(Map map) {
        foreground = createImage(map.getDim());

        Graphics2D g = foreground.createGraphics();

        Draw.drawForeTiles(g, map, 16, false);
        g.dispose();

        BufferedImage resized = getResizedImage(foreground);

        foreLayer.setIcon(new ImageIcon(resized));
        foreLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawForeLayerUpdate() {
        if (foreground == null)
            return;
        BufferedImage resized = getResizedImage(foreground);

        foreLayer.setIcon(new ImageIcon(resized));
        foreLayer.setBounds(0, 0, resized.getWidth(), resized.getHeight());
    }

    public void drawNpcLayer(Map map) {
        if (npcImage == null)
            npcImage = createImage(map.getDim());

        Point delta = getDelta();
        delta = new Point(delta.x / 16, delta.y / 16);
        Dimension size = getMySize();
        Point end = new Point(delta.x + size.width / 16, delta.y + size.height / 16);

        clearRectBi(npcImage, delta, end);

        Graphics2D g = npcImage.createGraphics();
        Draw.drawNPC(g, map, 16, delta, end);
        g.dispose();

        BufferedImage resized = getResizedImage(npcImage);

        npcLayer.setIcon(new ImageIcon(resized));
        npcLayer.setBounds(0, 0,  resized.getWidth(), resized.getHeight());
    }

    public void drawPlayerLayer(EngineState state) {
        ImportedTile t = state.player.getAnim().toImportedTile(state.player.getDirection());

        playerLayer.setIcon(new ImageIcon(t.get()));
        Point delta = getDelta();
        Point playerPos = new Point((int)(state.player.getPosition().getX() * 16), (int)(state.player.getPosition().getY() * 16));
        playerLayer.setBounds(playerPos.x - delta.x, playerPos.y - delta.y, t.getWidth() * 16, t.getHeight() * 16);
    }

    public void drawTimeCycleLayer(Map map, boolean isNight) {
        if (timeCycleImage == null) {
            Dimension size = getMySize();
            timeCycleImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = timeCycleImage.createGraphics();
            int alpha = 100;
            g.setColor(new Color(100, 100, 100, alpha));
            g.fillRect(0,0, timeCycleImage.getWidth(), timeCycleImage.getHeight());
            g.dispose();

            timeCycleLayer.setIcon(new ImageIcon(timeCycleImage));
            timeCycleLayer.setBounds(0, 0, timeCycleImage.getWidth(), timeCycleImage.getHeight());
        }
        timeCycleLayer.setVisible(isNight);
    }

    public void drawAll(EngineState state) {
        npcImage = null;

        drawBackLayer(state.currentMap);
        drawForeLayer(state.currentMap);
        drawNpcLayer(state.currentMap);
        drawPlayerLayer(state);
        if (state.world.timeCycle.isActive())
            drawTimeCycleLayer(state.currentMap, state.world.timeCycle.isNight());
        Dimension size = getMySize();
        pauseLayer.setBounds(0, 0, size.width, size.height);
        dialogLayer.setBounds(0, 0, size.width, size.height);
        inventoryLayer.setBounds(0, 0, size.width, size.height);
    }

    public void drawUpdate (EngineState state) {
        drawBackLayerUpdate();
        drawForeLayerUpdate();
        drawNpcLayer(state.currentMap);
        drawPlayerLayer(state);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof EngineState && o instanceof String) {
            EngineState state = (EngineState)observable;
            String str = (String)o;
            if (str.equals("Change Map")) {
                drawAll(state);
                setSizeMap();
                repaint();
            }
            else if (str.equals("Update Perso")) {
                drawUpdate(state);
                repaint();
            }
            else if (str.equals("Update NPC")) {
                drawNpcLayer(state.currentMap);
                repaint();
            }
            else if (str.equals("Pause")) {
                pauseLayer.setVisible(true);
            }
            else if (str.equals("Resume")) {
                pauseLayer.setVisible(false);
            }
            else if (str.equals("Update Message")) {
                dialog.setText(EngineState.getInstance().currentMessage.getMessage());
                dialog.setName(EngineState.getInstance().currentMessage.getName());
                dialogLayer.setVisible(true);
            }
            else if (str.equals("Remove Message")) {
                dialog.setText("");
                dialog.setName("");
                dialogLayer.setVisible(false);
            }
            else if (str.equals("Show Inventory")) {
                inventory.updateInventory();
                inventoryLayer.setVisible(true);
            }
            else if (str.equals("Hide Inventory")) {
                inventoryLayer.setVisible(false);
            }
        }
    }

    private BufferedImage getResizedImage(BufferedImage image) {
        Point delta = getDelta();
        Dimension size = getMySize();

        return image.getSubimage(delta.x, delta.y, size.width, size.height);
    }

    public static void clearRectBi(BufferedImage bi, Point in, Point out) {
        Graphics2D g = bi.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 255, 0, 0));
        g.fillRect(in.x * 16, in.y * 16, (out.x - in.x + 1) * 16, (out.y - in.y + 1) * 16);
        g.dispose();
    }

    private Dimension getMySize() {
        Map map = EngineState.getInstance().currentMap;
        int width = Math.min(992, map.getDim().width * 16);
        int height = Math.min(688, map.getDim().height * 16);
        return new Dimension(width, height);
    }

    private Point getDelta() {
        Map map = EngineState.getInstance().currentMap;
        int width = Math.min(992, map.getDim().width * 16);
        int height = Math.min(688, map.getDim().height * 16);

        Player p = EngineState.getInstance().player;

        int deltaX = 0;
        int deltaY = 0;

        Point2D pos = p.getPosition();

        if (pos.getX() * 16 > width / 2) {
            if (pos.getX() * 16 > (map.getDim().width * 16) - width / 2)
                deltaX = (map.getDim().width * 16) - width;
            else
                deltaX = (int) ((pos.getX() * 16) - (width / 2));
        }
        if (pos.getY() * 16 > height / 2) {
            if (pos.getY() * 16 > (map.getDim().height * 16) - height / 2)
                deltaY = (map.getDim().height * 16) - height;
            else
                deltaY = (int) ((pos.getY() * 16) - (height / 2));
        }
        return new Point(deltaX, deltaY);
    }

    private void setSizeMap() {
        Map map = EngineState.getInstance().currentMap;

        Dimension size = getMySize();

        System.out.println(size.width + " " + size.height);
        this.setPreferredSize(new Dimension(size.width, size.height));
        this.setSize(new Dimension(size.width, size.height));

        Engine.validateAll(this);
    }
}

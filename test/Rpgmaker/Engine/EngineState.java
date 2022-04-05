package Rpgmaker.Engine;

import Rpgmaker.Model.World.*;
import Rpgmaker.Tools.FileManager;
import Rpgmaker.Tools.Pair;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.util.Observable;
import java.util.Optional;
import java.util.Vector;

public class EngineState extends Observable {
    public World world;
    public Map currentMap;
    public Player player;
    public NPC currentMessage;
    EngineController controller;

    static EngineState engineState;

    public EngineState(World world, EngineController controller) {
        engineState = this;
        this.controller = controller;
        this.world = new World(world);
        this.player = new Player(world.getPlayer());
        this.currentMessage = null;
    }

    public static EngineState getInstance() {
        return engineState;
    }

    public void init() {
        Map newMap = world.getMapById(world.getPlayer().getMapId());
        changeMap(newMap);
    }

    public void changeMap(Map map) {
        currentMap = map;
        setChanged();
        notifyObservers("Change Map");
    }

    public void redrawPerso() {
        setChanged();
        notifyObservers("Update Perso");
    }

    public boolean movePerso(Direction dir, int delta_time) {
        if (!currentMap.checkBoundsPerso(player, dir, player.getPosition(), delta_time)) {
            player.move(dir, delta_time);
            return true;
        }
        return false;
    }

    public boolean talk() {
        boolean does_action = false;

        Optional<NPC> npc = currentMap.getNpcs().stream().min((o1, o2) -> {
            double dist1 = o1.getPoint().distance(player.getPosition());
            double dist2 = o2.getPoint().distance(player.getPosition());
            return (int) (dist1 - dist2);
        });

        if (npc.isPresent()) {
            var centerPos = new Point2D.Double(player.getPosition().getX() + 0.5f, player.getPosition().getY() + 0.5f);
            var centerPosNPC = new Point2D.Double(npc.get().getPoint().getX() + 0.5f, npc.get().getPoint().getY() + 0.5f);
            if (centerPosNPC.distance(centerPos) < 2) {
                currentMessage = npc.get();
                setChanged();
                notifyObservers("Update Message");
                does_action = true;
            }
        }
        return does_action;
    }

    public boolean pickObject() {
        var dimPt = getDimPointFromDirection();
        Point occupied = currentMap.isOccupied(dimPt.right.x, dimPt.right.y, dimPt.left);
        if (occupied != null) {
            Foreground f = currentMap.getForegroundSet().get(occupied);
            if (f.isPickable && (!f.isHided || f.isShowed) && !f.isRemoved) {
                System.out.println("Pick object " + currentMap.getForegroundSet().get(occupied).getName());
                player.getItems().add(f);
                f.isRemoved = true;
                setChanged();
                notifyObservers("Update foreground");
                return true;
            }
        }
        return false;
    }

    public boolean destroyObject() {
        var dimPt = getDimPointFromDirection();
        Point occupied = currentMap.isOccupied(dimPt.right.x, dimPt.right.y, dimPt.left);
        if (occupied != null) {
            Foreground f = currentMap.getForegroundSet().get(occupied);
            if (f.isBreakable && !f.isRemoved && (!f.isHided || f.isShowed) && player.hasItem(f.getBreaker())) {
                System.out.println("Destroy object " + currentMap.getForegroundSet().get(occupied).getName());
                player.getItems().add(f);
                f.isRemoved = true;

                Dimension dim = f.getImported().getDimention();
                for (int i = occupied.x; i < occupied.x + dim.width; i++) {
                    for (int j = occupied.y; j < occupied.y + dim.height; j++) {
                        currentMap.setWalkable(i, j, true);
                    }
                }
                setChanged();
                notifyObservers("Update foreground");
                return true;
            }
        }
        return false;
    }

    public void shutUp() {
        Vector<Point> v = currentMessage.getRevealForeground();
        boolean hasChanged = false;
        for (Point p : v) {
            if (currentMap.getForegroundSet().get(p).isHided && !currentMap.getForegroundSet().get(p).isShowed) {
                currentMap.getForegroundSet().get(p).isShowed = true;
                hasChanged = true;
                System.out.println("Showing " + currentMap.getForegroundSet().get(p));
            }
        }
        if (hasChanged) {
            setChanged();
            notifyObservers("Update foreground");
        }
        setChanged();
        notifyObservers("Remove Message");
    }

    public void checkTeleporter() {
        var realPos = new Point2D.Double(player.getPosition().getX(), player.getPosition().getY() + 1);
        var realPos2 = new Point2D.Double(player.getPosition().getX() + 1, player.getPosition().getY() + 1);
        for (Teleporter t : currentMap.getTeleporters()) {
            if (t.getPosition().distance(realPos) < 0.5f || t.getPosition().distance(realPos2) < 0.5f) {
                if (t.getPointDest() == null)
                    return;
                player.setPosition(new Point2D.Double(t.getPointDest().getX(), t.getPointDest().getY()), t.getMapDestId());
                if (currentMap.id != t.getMapDestId())
                    changeMap(world.getMapById(t.getMapDestId()));
                System.out.println("Teleport to : " + currentMap.toString());
            }
        }
    }

    public void setPause(boolean b) {
        setChanged();
        if (b)
            notifyObservers("Pause");
        else
            notifyObservers("Resume");
    }

    public int switchTime() {
        world.timeCycle.switchTime();
        setChanged();
        notifyObservers("Switch Time");
        return  world.timeCycle.getNextDelay();
    }

    public void redrawNPC() {
        setChanged();
        notifyObservers("Update NPC");
    }

    public boolean showInv() {
        setChanged();
        notifyObservers("Show Inventory");
        return true;
    }

    public boolean hideInv() {
        setChanged();
        notifyObservers("Hide Inventory");
        return true;
    }

    public Pair<Dimension, Point> getDimPointFromDirection() {
        Point player1D = new Point(Math.round((float)player.getPosition().getX()), Math.round((float)player.getPosition().getY()));
        Direction dir = player.getDirection();
        Dimension dim = null;
        switch (dir) {
            case DOWN:
                dim = new Dimension(2, 3);
                break;
            case LEFT:
                dim = new Dimension(3, 2);
                player1D = new Point(player1D.x - 1, player1D.y);
                break;
            case RIGHT:
                dim = new Dimension(3, 2);
                break;
            case UP:
                dim = new Dimension(2, 3);
                player1D = new Point(player1D.x, player1D.y - 1);
                break;
        }
        return new Pair<>(dim, player1D);
    }

    public void saveState() {
        FileManager.saveFile(new SaveState(this), ".save");
    }

    public void loadState() {
        File file = FileManager.getFile();
        if (file == null)
            return;
        if (JOptionPane.showConfirmDialog(Engine.getInstance(), "Do you really want to load this save state ?\n" +
                "You will loose your current progression.") != 0)
            return;
        Gson gson = new Gson();
        try {
            SaveState saveState = gson.fromJson(new FileReader(file), SaveState.class);
            saveState.updateWorld(this);
            controller.keyState.set(KeyEvent.VK_P, true);
            changeMap(world.getMapById(player.getMapId()));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(Engine.getInstance(), "This save state is corrupt !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

    }
}

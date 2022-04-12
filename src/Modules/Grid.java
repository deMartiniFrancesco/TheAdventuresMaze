package Modules;

import Interfaces.Entities.EntityInterface;
import Main.Game;
import Main.States;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class Grid {

    private final Game main;
    private final int lenCampo = 25;

    public int visitate = 0;

    private final ArrayList<Cell> cells = new ArrayList<>(lenCampo * lenCampo);

    private final Stack<Cell> cellStack = new Stack<>();

    private Player player;

    private Cell current;

    public Grid(Game main) {
        this.main = main;

        initGrid();

        current = cells.get(0);
    }


    public void addPlayer(){
        player = new Player(cells.get(0));
    }

    private void initGrid() {
        for (int row = 0; row < lenCampo; row++) {
            for (int col = 0; col < lenCampo; col++) {
                Cell cell = new Cell(col, row, this);
                cells.add(cell);
            }
        }
    }

    public void generateMaze() {
        visitate = Math.min(lenCampo * lenCampo, visitate + 1);
        current.setVisitated(true);
        Cell next = current.checkNeighbors();
        if (next != null) {
            next.setVisitated(true);

            cellStack.push(current);

            removeWalls(current, next);

            current = next;
        } else {
            if (!cellStack.isEmpty()) {
                current = cellStack.pop();
            } else {
                main.changeState(States.PLAING);
                return;
            }
        }
        generateMaze();
    }

    public void removeWalls(Cell a, Cell b) {
        int x = a.getCol() - b.getCol();

        if (x == 1) {
            a.modifyWall(3, false);
            b.modifyWall(1, false);
            return;
        }
        if (x == -1) {
            a.modifyWall(1, false);
            b.modifyWall(3, false);
            return;
        }


        int y = a.getRow() - b.getRow();
        if (y == 1) {
            a.modifyWall(0, false);
            b.modifyWall(2, false);
            return;
        }
        if (y == -1) {
            a.modifyWall(2, false);
            b.modifyWall(0, false);
        }

    }

    public ArrayList<Cell> getCells() { return cells; }

    public int getLenCampo() { return lenCampo; }

    public Player getPlayer() {
        return player;
    }

}



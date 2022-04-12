package Modules;

import Main.Game;

import java.util.ArrayList;
import java.util.Stack;

public class Grid {

    private final Game main;
    private final int lenCampo = 70;

    private final ArrayList<Cell> cells = new ArrayList<>(lenCampo * lenCampo);

    private final Stack<Cell> cellStack = new Stack<>();


    private Cell current;

    public Grid(Game main) {
        this.main = main;

        initGrid();

        current = cells.get(0);
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    public int getLenCampo() {
        return lenCampo;
    }

    public Cell getCurrent() {
        return current;
    }

    public void setCurrent(Cell current) {
        this.current = current;
    }

    private void initGrid() {
        for (int row = 0; row < lenCampo; row++) {
            for (int col = 0; col < lenCampo; col++) {
                Cell cell = new Cell(col, row, this);
                cells.add(cell);
            }
        }
    }

    public void removeWalls(Cell a, Cell b){
        int x = a.getCol() - b.getCol();

        if (x == 1){
            a.modifyWall(3, false);
            b.modifyWall(1, false);
            return;
        }
        if (x == -1){
            a.modifyWall(1, false);
            b.modifyWall(3, false);
            return;
        }


        int y = a.getRow() - b.getRow();
        if (y == 1){
            a.modifyWall(0, false);
            b.modifyWall(2, false);
            return;
        }
        if (y == -1){
            a.modifyWall(2, false);
            b.modifyWall(0, false);
        }

    }
}



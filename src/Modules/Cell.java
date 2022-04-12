package Modules;

import java.util.ArrayList;
import java.util.Random;

public class Cell {
    private final int row, col;
    public static int DIMENTION = 10;

    private final Grid grid;

    private final boolean[] walls = {true, true, true, true};

    private boolean visitated = false;

    public Cell(int col, int row, Grid grid) {
        this.row = row;
        this.col = col;
        this.grid = grid;
    }

    private int index(int col, int row) {
        if (col < 0 || row < 0 || col >= grid.getLenCampo() || row >= grid.getLenCampo()) {
            return -1;
        }
        return col + row * grid.getLenCampo();
    }

    public Cell checkNeighbors() {

        ArrayList<Cell> neighbors = new ArrayList<>();


        ArrayList<Cell> cells = grid.getCells();

        Cell top = index(col, row - 1) == -1 ? null : cells.get(index(col, row - 1));
        Cell right = index(col + 1, row) == -1 ? null : cells.get(index(col + 1, row));
        Cell bottom = index(col, row + 1) == -1 ? null : cells.get(index(col, row + 1));
        Cell left = index(col - 1, row) == -1 ? null : cells.get(index(col - 1, row));

        if (top != null && !top.isVisitated()) {
            neighbors.add(top);
        }
        if (right != null && !right.isVisitated()) {
            neighbors.add(right);
        }
        if (bottom != null && !bottom.isVisitated()) {
            neighbors.add(bottom);
        }
        if (left != null && !left.isVisitated()) {
            neighbors.add(left);
        }

        if (!neighbors.isEmpty()) {
            int randomNumber = new Random().nextInt(neighbors.size());
            return neighbors.get(randomNumber);
        } else {
            return null;
        }

    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void modifyWall(int index, boolean value) {
        this.walls[index] = value;
    }

    public boolean isVisitated() {
        return visitated;
    }

    public void setVisitated(boolean visitated) {
        this.visitated = visitated;
    }
}

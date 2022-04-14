package Modules;

import Main.Game;
import Main.States;

import java.util.ArrayList;
import java.util.Random;

public class Grid {

    private final Game main;

    public int visitate = 0;

    private final ArrayList<Cell> cells = new ArrayList<>((int) Math.sqrt(Game.level));

    private Player player;

    private final Random random = Game.istance.random;

    private Cell finish;

    private Cell current;

    public int mutationCouter = 0;
    public int isVisitedCouter = 0;

    public Grid(Game main) {
        this.main = main;

        initGrid();

        current = cells.get(0);
    }


    public void addPlayer() {
        player = new Player(cells.get(0));
    }


    private int index(int col, int row) {
        if (col < 0 || row < 0 || col >= Game.level || row >= Game.level) {
            return -1;
        }
        return col + row * Game.level;
    }

    private void initGrid() {
        for (int row = 0; row < Game.level; row++) {
            for (int col = 0; col < Game.level; col++) {
                Cell cell = new Cell(col, row);
                cells.add(cell);
            }
        }

        for (int row = 0; row < Game.level; row++) {
            for (int col = 0; col < Game.level; col++) {
                Cell targetCell = cells.get(index(col, row));

                targetCell.setTop(index(col, row - 1) == -1 ? null : cells.get(index(col, row - 1)));
                targetCell.setRight(index(col + 1, row) == -1 ? null : cells.get(index(col + 1, row)));
                targetCell.setBottom(index(col, row + 1) == -1 ? null : cells.get(index(col, row + 1)));
                targetCell.setLeft(index(col - 1, row) == -1 ? null : cells.get(index(col - 1, row)));
            }
        }

        finish = cells.get(cells.size() - 1);

    }


    public void generateMaze() {
        boolean loop = calculateCurrent();
        while (!loop || isVisitedCouter < Math.sqrt(Game.level)) {
            loop = calculateCurrent();
            isVisitedCouter = 0;
            cells.forEach(cell -> {
                if (cell.isVisitated()) {
                    isVisitedCouter++;
                }
            });
        }
        main.changeState(States.PLAING);
    }

    public boolean calculateCurrent() {
        visitate = Math.min((int) Math.sqrt(Game.level), visitate + 1);
        current.setVisitated(true);
        Cell next = current.checkNeighbors();
        if (next != null) {
            next.setVisitated(true);

            next.addPrecedent(current);

            removeWalls(current, next);

            current = next;
        } else {
            if (current.pickPrecedent() != null) {
                if (mutationCouter < Game.level * 2 && random.nextInt(Game.level * 2) == 0) {
                    mutationCouter++;
                    next = current.getCasualNotNullNeighbor(current);

                    next.addPrecedent(current);

                    removeWalls(current, next);

                    current = next;
                } else {
                    current = current.getPrecedent();
                }
            } else {
                return true;
            }
        }
        return false;
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

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public int getLenCampo() {
        return Game.level;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell getFinish() {
        return finish;
    }
}



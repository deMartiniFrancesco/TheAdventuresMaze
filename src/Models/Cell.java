package Models;

import Main.Game;

import java.util.*;

public class Cell {


    private final Game game = Game.istance;
    private final Random random = game.random;

    private final int row, col;
    public static int DIMENTION = 20;


    private final ArrayList<Boolean> walls = new ArrayList<>(4);

    private final ArrayList<Cell> neighborsCell = new ArrayList<>(4);

    private boolean visitated = false;


    private final Stack<Cell> precedentStack = new Stack<>();

    public Cell(int col, int row) {
        this.row = row;
        this.col = col;

        walls.addAll(List.of(
                true,   //TOP
                true,   //RIGTH
                true,   //BOTTOM
                true    //LEFT
        ));


    }


    public Cell checkNeighbors() {

        ArrayList<Cell> neighbors = new ArrayList<>();

        if (getTop() != null && !getTop().isVisitated()) {
            neighbors.add(getTop());
        }
        if (getRight() != null && !getRight().isVisitated()) {
            neighbors.add(getRight());
        }
        if (getBottom() != null && !getBottom().isVisitated()) {
            neighbors.add(getBottom());
        }
        if (getLeft() != null && !getLeft().isVisitated()) {
            neighbors.add(getLeft());
        }

        if (!neighbors.isEmpty()) {
            int randomNumber = random.nextInt(neighbors.size());
            return neighbors.get(randomNumber);

        } else {
            return null;
        }

    }

    public Cell[] getTrueNeighbors() {

        Cell[] neighbors = new Cell[4];

        if (getTop() != null && !walls.get(PlayerKeyAction.TOP.getDirectionIndex())) {
            neighbors[PlayerKeyAction.TOP.getDirectionIndex()] = getTop();
        }
        if (getRight() != null && !walls.get(PlayerKeyAction.RIGHT.getDirectionIndex())) {
            neighbors[PlayerKeyAction.RIGHT.getDirectionIndex()] = getRight();
        }
        if (getBottom() != null && !walls.get(PlayerKeyAction.BOTTOM.getDirectionIndex())) {
            neighbors[PlayerKeyAction.BOTTOM.getDirectionIndex()] = getBottom();
        }
        if (getLeft() != null && !walls.get(PlayerKeyAction.LEFT.getDirectionIndex())) {
            neighbors[PlayerKeyAction.LEFT.getDirectionIndex()] = getLeft();
        }

        return neighbors;
    }

    public Cell getCasualNotNullNeighbor(Cell exlude) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        if (getTop() != null && getTop() != exlude) {
            neighbors.add(getTop());
        }
        if (getRight() != null && getRight() != exlude) {
            neighbors.add(getRight());
        }
        if (getBottom() != null && getBottom() != exlude) {
            neighbors.add(getBottom());
        }
        if (getLeft() != null && getLeft() != exlude) {
            neighbors.add(getLeft());
        }
        return neighbors.get(random.nextInt(neighbors.size()));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Boolean> getWalls() {
        return walls;
    }

    public void removeWall(int index) {
        this.walls.set(index, false);
    }

    public boolean isVisitated() {
        return visitated;
    }

    public void setVisitated(boolean visitated) {
        this.visitated = visitated;
    }


    public Cell getPrecedent() {
        if (precedentStack.isEmpty()) {
            return null;
        }
        return precedentStack.pop();
    }

    public Cell pickPrecedent() {
        if (precedentStack.isEmpty()) {
            return null;
        }
        return precedentStack.peek();
    }

    public void addPrecedent(Cell precedent) {
        this.precedentStack.push(precedent);
    }


    public Cell getTop(){
        return neighborsCell.get(PlayerKeyAction.TOP.getDirectionIndex());
    }
    public Cell getRight(){
        return neighborsCell.get(PlayerKeyAction.RIGHT.getDirectionIndex());
    }
    public Cell getBottom(){
        return neighborsCell.get(PlayerKeyAction.BOTTOM.getDirectionIndex());
    }
    public Cell getLeft(){
        return neighborsCell.get(PlayerKeyAction.LEFT.getDirectionIndex());
    }

    public void setTop(Cell top) {
        neighborsCell.set(PlayerKeyAction.TOP.getDirectionIndex(), top);
    }

    public void setRight(Cell right) {
        neighborsCell.set(PlayerKeyAction.RIGHT.getDirectionIndex(), right);
    }

    public void setBottom(Cell bottom) {
        neighborsCell.set(PlayerKeyAction.BOTTOM.getDirectionIndex(), bottom);
    }

    public void setLeft(Cell left) {
        neighborsCell.set(PlayerKeyAction.LEFT.getDirectionIndex(), left);
    }
}

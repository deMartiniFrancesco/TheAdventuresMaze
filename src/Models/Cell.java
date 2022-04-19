package Models;

import Main.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Cell {
    private final int row, col;
    public static int DIMENTION = 20;

    private final Random random = Game.istance.random;

    private final boolean[] walls = {
            true,   //TOP
            true,   //RIGTH
            true,   //BOTTOM
            true    //LEFT
    };

    private boolean visitated = false;


    private Cell top = null;
    private Cell right = null;
    private Cell bottom = null;
    private Cell left = null;


    private final Stack<Cell> precedentStack = new Stack<>();

    public Cell(int col, int row) {
        this.row = row;
        this.col = col;
    }


    public Cell checkNeighbors() {

        ArrayList<Cell> neighbors = new ArrayList<>();

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
            int randomNumber = random.nextInt(neighbors.size());
            return neighbors.get(randomNumber);

        } else {
            return null;
        }

    }

    public Cell[] getTrueNeighbors(){

        Cell[] neighbors = new Cell[4];

        if (top != null && !walls[PlayerKeyAction.TOP.getDirectionIndex()]) {
            neighbors[PlayerKeyAction.TOP.getDirectionIndex()] = top;
        }
        if (right != null && !walls[PlayerKeyAction.RIGHT.getDirectionIndex()]) {
            neighbors[PlayerKeyAction.RIGHT.getDirectionIndex()] = right;
        }
        if (bottom != null && !walls[PlayerKeyAction.BOTTOM.getDirectionIndex()]) {
            neighbors[PlayerKeyAction.BOTTOM.getDirectionIndex()] = bottom;
        }
        if (left != null && !walls[PlayerKeyAction.LEFT.getDirectionIndex()]) {
            neighbors[PlayerKeyAction.LEFT.getDirectionIndex()] = left;
        }

        return neighbors;
    }

    public Cell getCasualNotNullNeighbor(Cell exlude){
        ArrayList<Cell> neighbors = new ArrayList<>();

        if (top != null && top != exlude) {
            neighbors.add(top);
        }
        if (right != null && right != exlude) {
            neighbors.add(right);
        }
        if (bottom != null && bottom != exlude) {
            neighbors.add(bottom);
        }
        if (left != null && left != exlude) {
            neighbors.add(left);
        }
        return neighbors.get(random.nextInt(neighbors.size()));
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

    public void removeWall(int index) {
        this.walls[index] = false;
    }

    public boolean isVisitated() {
        return visitated;
    }

    public void setVisitated(boolean visitated) {
        this.visitated = visitated;
    }


    public Cell getPrecedent() {
        if (precedentStack.isEmpty()){
            return null;
        }
        return precedentStack.pop();
    }

    public Cell pickPrecedent(){
        if (precedentStack.isEmpty()){
            return null;
        }
        return precedentStack.peek();
    }

    public void addPrecedent(Cell precedent) {
        this.precedentStack.push(precedent);
    }

    public void setTop(Cell top) {
        this.top = top;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public void setBottom(Cell bottom) {
        this.bottom = bottom;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }
}
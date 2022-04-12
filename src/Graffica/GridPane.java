package Graffica;

import Modules.Cell;
import Modules.Grid;

import javax.swing.*;
import java.awt.*;

public class GridPane extends JPanel {
    Grid grid;
    boolean finished = false;

    public GridPane(Grid grid) {
        this.grid = grid;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        grid.getCurrent().setVisitated(true);
        Cell next = grid.getCurrent().checkNeighbors();
        if (next != null) {
            next.setVisitated(true);

            grid.getCellStack().push(grid.getCurrent());

            grid.removeWalls(grid.getCurrent(), next);

            grid.setCurrent(next);
        } else {
            if (!grid.getCellStack().isEmpty()) {
                grid.setCurrent(grid.getCellStack().pop());
            } else {
                finished = true;
            }
        }

        int dimension = Cell.DIMENTION;

        for (Cell cell : grid.getCells()) {
            boolean[] walls = cell.getWalls();
            int x = cell.getCol() * dimension;
            int y = cell.getRow() * dimension;

            if (cell.isVisitated()) {
                g.setColor(Color.orange);
                g.fillRect(x, y, dimension, dimension);
            }
            if (cell.equals(grid.getCurrent())) {
                g.setColor(Color.red);
                g.fillRect(x, y, dimension, dimension);
            }
            g.setColor(Color.GRAY);
            if (walls[0]) { // TOP
                g.drawLine(x, y, x + dimension, y);
            }
            if (walls[1]) { //RIGTH
                g.drawLine(x + dimension, y, x + dimension, y + dimension);
            }
            if (walls[2]) { //BOTTOM
                g.drawLine(x, y + dimension, x + dimension, y + dimension);
            }
            if (walls[3]) { //LEFTH
                g.drawLine(x, y, x, y + dimension);
            }

            if (!finished) {
                repaint();
            }

        }
    }

}
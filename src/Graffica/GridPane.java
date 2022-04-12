package Graffica;

import Main.Game;
import Main.States;
import Modules.Cell;
import Modules.Grid;

import javax.swing.*;
import java.awt.*;

public class GridPane extends JPanel {

    Game main;
    Grid grid;


    int dimension = Cell.DIMENTION;
    public GridPane(Game main, Grid grid) {
        this.main = main;
        this.grid = grid;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dimension * grid.getLenCampo(), dimension * grid.getLenCampo());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (main.state == States.PLAING) {


            for (Cell cell : grid.getCells()) {
                boolean[] walls = cell.getWalls();
                int x = cell.getCol() * dimension;
                int y = cell.getRow() * dimension;

                if (cell.isVisitated()) {
                    g.setColor(Color.orange);
                    g.fillRect(x, y, dimension, dimension);
                }
                if (cell.equals(grid.getPlayer().getCell())) {
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
            }
        }
    }
}
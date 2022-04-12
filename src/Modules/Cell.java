package Modules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JComponent {

    private int row, col;
    int dimension = 20;


    boolean[] walls = {true, true, true, true};

    boolean visitated = false;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("row = %d, col = %d\n", row, col);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = col * dimension;
        int y = row * dimension;

        g.drawLine(x, y, x + dimension, y + dimension);
        g.drawLine(x + dimension, y, x + dimension, y + dimension);
        g.drawLine(x, y + dimension, x + dimension, y + dimension);
        g.drawLine(x, y, x, y+dimension);
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}

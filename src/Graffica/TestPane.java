package Graffica;

import Modules.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TestPane extends JPanel {

    private int length;
    private final ArrayList<Cell> cells;
    private Point selectedCell;

    public TestPane(int length) {
        this.length = length;
        cells = new ArrayList<>(length * length);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void invalidate() {
        cells.clear();
        selectedCell = null;
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / length;
        int cellHeight = height / length;

        int xOffset = (width - (length * cellWidth)) / 2;
        int yOffset = (height - (length * cellHeight)) / 2;

        if (cells.isEmpty()) {
            for (int row = 0; row < length; row++) {
                for (int col = 0; col < length; col++) {
//                    Rectangle cell = new Rectangle(
//                            xOffset + (col * cellWidth),
//                            yOffset + (row * cellHeight),
//                            cellWidth,
//                            cellHeight);
                    cells.add(new Cell(row, col));
                }
            }
        }

        g2d.setColor(Color.GRAY);
        for (Cell cell : cells) {
            g2d.draw();
        }

        g2d.dispose();
    }
}
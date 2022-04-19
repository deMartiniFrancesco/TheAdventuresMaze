package Grafica;

import Action.Listener.MotionActionListener;
import Control.Interfaces.MovableEntity;
import Control.Interfaces.WindowPanel;
import Main.Game;
import Main.States;
import Models.Cell;
import Models.PlayerKeyAction;
import Models.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class GridPane extends JPanel implements WindowPanel {

    private final Game game = Game.istance;

    private final Grid grid = game.grid;

    private final int dimension = Cell.DIMENTION;

    @Override
    public void addAction(List<?> args) {
        KeyStroke pressedKeyStroke;
        PlayerKeyAction direction;
        MovableEntity target;
        try {
            pressedKeyStroke = (KeyStroke) args.get(0);
            direction = (PlayerKeyAction) args.get(1);
            target = (MovableEntity) args.get(2);
        } catch (Exception ignored) {
            return;
        }

        String name = pressedKeyStroke.toString();

        MotionActionListener action = new MotionActionListener(name, direction, target);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        getActionMap().put(name, action);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dimension * grid.getLenCampo(), dimension * grid.getLenCampo());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.state == States.PLAING) {


            for (Cell cell : grid.getCells()) {
                boolean[] walls = cell.getWalls();
                int x = cell.getCol() * dimension;
                int y = cell.getRow() * dimension;

                if (cell.isVisitated()) {
                    g.setColor(Color.orange);
                    g.fillRect(x, y, dimension, dimension);
                }
                if (cell.equals(grid.getFinish())) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, dimension, dimension);
                }
                if (cell.equals(grid.getPlayer().getCell())) {
                    g.setColor(Color.red);
                    g.fillRect(x + 2, y + 2, dimension - 4, dimension - 4);
                }
                g.setColor(Color.BLACK);
                if (walls[PlayerKeyAction.TOP.getDirectionIndex()]) { // TOP
                    g.drawLine(x, y, x + dimension, y);
                }
                if (walls[PlayerKeyAction.RIGHT.getDirectionIndex()]) { //RIGHT
                    g.drawLine(x + dimension, y, x + dimension, y + dimension);
                }
                if (walls[PlayerKeyAction.BOTTOM.getDirectionIndex()]) { //BOTTOM
                    g.drawLine(x, y + dimension, x + dimension, y + dimension);
                }
                if (walls[PlayerKeyAction.LEFT.getDirectionIndex()]) { //LEFT
                    g.drawLine(x, y, x, y + dimension);
                }
            }
        }
    }
}
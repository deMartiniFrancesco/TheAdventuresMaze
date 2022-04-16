package Graffica;

import Action.MotionActionListener;
import Interfaces.MovableEntity;
import Interfaces.WindowPanel;
import Main.Game;
import Main.States;
import Modules.Cell;
import Modules.PlayerKeyAction;
import Modules.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridPane extends JPanel implements WindowPanel {

    Game main;
    Grid grid;


    int dimension = Cell.DIMENTION;

    public GridPane(Game main) {
        this.main = main;
        this.grid = Game.istance.grid;
    }

    @Override
    public void addAction(List<?> args) {
        String name;
        PlayerKeyAction direction;
        MovableEntity target;
        try {
            name = (String) args.get(0);
            direction = (PlayerKeyAction) args.get(1);
            target = (MovableEntity) args.get(2);
        } catch (Exception ignored) {
            return;
        }
        MotionActionListener action = new MotionActionListener(name, direction, target);

        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
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

        if (main.state == States.PLAING) {


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
package Models;

import Control.Interfaces.MovableEntity;
import Control.Interfaces.WindowPanel;
import Grafica.GridPane;
import Main.Game;
import Main.States;

import java.util.List;

public class Player implements MovableEntity {


    private final Game game = Game.istance;

    private Cell cell;


    public Player(Cell cell) {
        this.cell = cell;
        addMotionSupport();
    }

    @Override
    public Cell getCell() {
        return cell;
    }


    private void addMotionSupport() {
        WindowPanel mainPane = (WindowPanel) Game.istance.mainPane;

        for (PlayerKeyAction action : PlayerKeyAction.values()) {
            mainPane.addAction(List.of(action.getKey(), action, this));
        }
    }


    @Override
    public void move(PlayerKeyAction playerKeyAction) {

        Cell target = cell.getTrueNeighbors()[playerKeyAction.getDirectionIndex()];

        if (target != null) {
            cell = target;
            game.mainPane.repaint();
            if (cell == game.grid.getFinish()) {
                game.actionListener.performAction(States.NEXT_LEVEL);
            }
        }
    }
}

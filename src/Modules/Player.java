package Modules;

import Graffica.GridPane;
import Interfaces.MovableEntity;
import Main.Game;
import Main.States;

import java.util.List;

public class Player implements MovableEntity {

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
        GridPane motion = (GridPane) Game.istance.mainPane;
        motion.addAction(List.of(PlayerKeyAction.TOP.getKey(), PlayerKeyAction.TOP, this));        //TOP
        motion.addAction(List.of(PlayerKeyAction.RIGHT.getKey(), PlayerKeyAction.RIGHT, this));    //RIGHT
        motion.addAction(List.of(PlayerKeyAction.BOTTOM.getKey(), PlayerKeyAction.BOTTOM, this));  //BOTTOM
        motion.addAction(List.of(PlayerKeyAction.LEFT.getKey(), PlayerKeyAction.LEFT, this));      //LEFT
    }


    @Override
    public void move(PlayerKeyAction playerKeyAction) {


        Cell target = cell.getTrueNeighbors()[playerKeyAction.getDirectionIndex()];


        if (target != null) {
            cell = target;
            Game.istance.mainPane.repaint();
            if (cell == Game.istance.grid.getFinish()) {
                Game.istance.changeState(States.FINISH);
            }

        }
    }
}

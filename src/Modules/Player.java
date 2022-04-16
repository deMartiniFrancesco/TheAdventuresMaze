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
        motion.addAction(List.of("W", Directions.TOP, this));        //TOP
        motion.addAction(List.of("D", Directions.RIGHT, this));      //RIGHT
        motion.addAction(List.of("S", Directions.BOTTOM, this));     //BOTTOM
        motion.addAction(List.of("A", Directions.LEFT, this));       //LEFT
    }


    @Override
    public void move(Directions directions) {


        Cell target = cell.getTrueNeighbors()[directions.getDirectionIndex()];


        if (target != null) {
            cell = target;
            Game.istance.mainPane.repaint();
            if (cell == Game.istance.grid.getFinish()){
                Game.istance.changeState(States.FINISH);
            }

        }
    }
}

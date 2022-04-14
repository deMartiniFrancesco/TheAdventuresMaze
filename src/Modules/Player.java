package Modules;

import Graffica.GridPane;
import Interfaces.Entities.MovableEntityInterface;
import Main.Game;
import Main.States;

public class Player implements MovableEntityInterface {

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
        GridPane motion = Game.istance.gridPane;
        motion.addAction("W", Directions.TOP, this);        //TOP
        motion.addAction("D", Directions.RIGHT, this);      //RIGHT
        motion.addAction("S", Directions.BOTTOM, this);     //BOTTOM
        motion.addAction("A", Directions.LEFT, this);       //LEFT
    }


    @Override
    public void move(Directions directions) {


        Cell target = cell.getTrueNeighbors()[directions.getDirectionIndex()];


        if (target != null) {
            cell = target;
            Game.istance.gridPane.repaint();
            if (cell == Game.istance.grid.getFinish()){
                Game.istance.changeState(States.FINISH);
            }

        }
    }
}

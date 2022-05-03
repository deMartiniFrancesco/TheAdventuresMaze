package Action.GameAction;

import Control.Timer;
import Main.Game;
import Models.Chronometer;

public class PlaingAction implements Runnable {

    private final Game game = Game.istance;


    @Override
    public void run() {

                /* TODO
                    unblind maze
                 */

        game.grid.addPlayer();
        game.chronometer = new Chronometer(10, Timer.DURATION_INFINITY);
        game.chronometer.start();

        game.mainPane.repaint();
    }
}

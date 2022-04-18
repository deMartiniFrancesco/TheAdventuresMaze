package Action;

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


        if (game.grid.getPlayer() == null) {
            game.grid.addPlayer();
            game.chronometer = new Chronometer(10, Timer.DURATION_INFINITY);
            game.chronometer.start();
        } else {
            System.out.println("Resume");
            game.chronometer.resume();
        }

        game.mainPane.repaint();
    }
}

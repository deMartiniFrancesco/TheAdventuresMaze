package Action;

import Main.Game;
import Main.States;

public class NextLevelAction implements Runnable {

    private final Game game = Game.istance;


    @Override
    public void run() {
        /* TODO
            save on file
        */
        Game.level++;
        System.out.println(game.chronometer.getElapsedTime());
        game.chronometer.cancel();
        game.actionListener.performAction(States.STARTING);
    }
}

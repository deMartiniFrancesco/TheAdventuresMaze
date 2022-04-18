package Action;

import Main.Game;

public class FinishAction implements Runnable {

    private final Game game = Game.istance;

    @Override
    public void run() {
        /* TODO
            save score on file
        */
        if (game.chronometer != null) {
            game.chronometer.cancel();
        }
        System.out.println("Finish");
        game.onDisable();
    }
}

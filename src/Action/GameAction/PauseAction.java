package Action.Game;

import Main.Game;

public class PauseAction implements Runnable {

    private final Game game = Game.istance;


    @Override
    public void run() {


                /* TODO
                    show pause menu
                    blind maze
                */
        System.out.println("Pause");
        game.chronometer.pause();
    }
}

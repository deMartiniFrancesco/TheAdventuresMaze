package Action.GameAction;

import Grafica.SettigsPanel;
import Main.Game;

public class SettingsAction implements Runnable {

    private final Game game = Game.istance;


    @Override
    public void run() {
        game.clearFrame();
        game.mainPane = new SettigsPanel();
        game.frame.addComponent(game.mainPane);
    }

}

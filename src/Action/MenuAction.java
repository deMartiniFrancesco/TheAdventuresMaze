package Action;

import Grafica.MenuPanel;
import Main.Game;

public class MenuAction implements Runnable {

    private final Game game = Game.istance;


    @Override
    public void run() {
        game.clearFrame();
        game.mainPane = new MenuPanel();
        game.frame.addComponent(game.mainPane);
    }

}

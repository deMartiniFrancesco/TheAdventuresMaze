package Action;

import Graffica.MenuPanel;
import Main.Game;

public class MenuAction implements Runnable{

    private final Game game = Game.istance;


    @Override
    public void run() {
        game.initFrame();
        game.mainPane = new MenuPanel();
        game.draw();
    }

}

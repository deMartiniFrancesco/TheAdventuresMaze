package Action;

import Grafica.GridPane;
import Main.Game;
import Models.Grid;

public class StartingAction implements Runnable{

    private final Game game = Game.istance;

    @Override
    public void run() {
        game.initFrame();
        game.grid = new Grid();
        game.mainPane = new GridPane();
        game.grid.generateMaze();
        game.frame.draw();
    }
}

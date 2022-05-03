package Action.GameAction;

import Grafica.GamePanel;
import Grafica.GridPane;
import Main.Game;
import Models.Grid;

public class StartingAction implements Runnable {

    private final Game game = Game.istance;

    @Override
    public void run() {
        game.clearFrame();
        game.grid = new Grid();
        game.mainPane = new GamePanel();
        game.grid.generateMaze();
        game.frame.addComponent(game.mainPane);

    }
}

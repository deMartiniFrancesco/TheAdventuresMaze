package Action.Listener;

import Action.*;
import Main.Game;
import Main.States;

public class GameActionListener {
    private final Game game = Game.istance;
    private final Runnable menuAction;
    private final Runnable startingAction;
    private final Runnable plaingAction;
    private final Runnable pauseAction;
    private final Runnable nextLevelAction;
    private final Runnable finishAction;


    public GameActionListener() {
        menuAction = new MenuAction();
        startingAction = new StartingAction();
        plaingAction = new PlaingAction();
        pauseAction = new PauseAction();
        nextLevelAction = new NextLevelAction();
        finishAction = new FinishAction();
    }

    public void performAction(States state) {
        game.state = state;

        switch (state) {
            case MENU -> menuAction.run();
            case STARTING -> startingAction.run();
            case PLAING -> plaingAction.run();
            case PAUSE -> pauseAction.run();
            case NEXT_LEVEL -> nextLevelAction.run();
            case FINISH -> finishAction.run();
        }
    }
}

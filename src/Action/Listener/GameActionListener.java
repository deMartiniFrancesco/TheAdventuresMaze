package Action.Listener;

import Action.*;
import Control.Interfaces.MovableEntity;
import Main.Game;
import Main.States;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener extends AbstractAction implements ActionListener {


    private final Game game = Game.istance;
    private final States state;

    private final Runnable menuAction;
    private final Runnable startingAction;
    private final Runnable plaingAction;
    private final Runnable pauseAction;
    private final Runnable nextLevelAction;
    private final Runnable finishAction;


    public GameActionListener(String name, States state) {
        super(name);
        this.state = state;
        menuAction = new MenuAction();
        startingAction = new StartingAction();
        plaingAction = new PlaingAction();
        pauseAction = new PauseAction();
        nextLevelAction = new NextLevelAction();
        finishAction = new FinishAction();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        game.state = state;
        switch (state) {
            case MENU ->        menuAction.run();
            case STARTING ->    startingAction.run();
            case PLAING ->      plaingAction.run();
            case PAUSE ->       pauseAction.run();
            case NEXT_LEVEL ->  nextLevelAction.run();
            case FINISH ->      finishAction.run();
        }


    }
}

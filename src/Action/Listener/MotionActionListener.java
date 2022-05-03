package Action.Listener;

import Control.Interfaces.MovableEntity;
import Main.Game;
import Main.States;
import Models.PlayerKeyAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MotionActionListener extends AbstractAction implements ActionListener {
    private final PlayerKeyAction playerKeyAction;
    private final Object target;
    private final Game game = Game.istance;

    public MotionActionListener(String nameCode, PlayerKeyAction playerKeyAction, Object target) {
        super(nameCode);
        this.playerKeyAction = playerKeyAction;
        this.target = target;
    }

    public void actionPerformed(ActionEvent e) {
        if (playerKeyAction.equals(PlayerKeyAction.PAUSE)) {
            game.actionListener.performAction(States.PAUSE);
            return;
        }
        if (playerKeyAction.equals(PlayerKeyAction.RESET)) {
            game.actionListener.performAction(States.STARTING);

        } else {
            if (target instanceof MovableEntity target){
                target.move(playerKeyAction);
            }
        }
    }
}
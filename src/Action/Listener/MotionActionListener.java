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
    private final MovableEntity target;

    public MotionActionListener(String name, PlayerKeyAction playerKeyAction, MovableEntity target) {
        super(name);
        this.playerKeyAction = playerKeyAction;
        this.target = target;
    }

    public void actionPerformed(ActionEvent e) {
        if (playerKeyAction.equals(PlayerKeyAction.PAUSE)) {
            System.out.println("Pause");
            Game.istance.setPause();
        }

        if (Game.istance.isPause){
            return;
        }
        if (playerKeyAction.equals(PlayerKeyAction.RESET)) {
            Game.istance.changeState(States.STARTING);

        }
        else {
            target.move(playerKeyAction);
        }
    }
}
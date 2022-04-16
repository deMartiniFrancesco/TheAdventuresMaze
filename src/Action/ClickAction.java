package Action;

import Interfaces.Action;
import Main.Game;
import Main.States;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickAction implements Action {
    private final States state;

    public ClickAction(States state) {
        this.state = state;
    }

    @Override
    public void run() {
        System.out.printf("Click %s\n", state);
        Game.istance.changeState(state);
    }
}
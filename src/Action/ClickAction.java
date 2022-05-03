package Action;

import Main.Game;
import Main.States;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClickAction implements Runnable {

    private final Game game = Game.istance;
    private final List<States> statesList = new ArrayList<>();

    public ClickAction(States state) {
        statesList.add(state);
    }

    public ClickAction(List<States> states) {
        statesList.addAll(states);
    }


    @Override
    public void run() {

        for (States state : statesList) {
            System.out.printf("Click %s\n", state);
            game.actionListener.performAction(state);
        }
    }
}
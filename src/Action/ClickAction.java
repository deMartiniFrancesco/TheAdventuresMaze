package Action;

import Main.Game;
import Main.States;

public class ClickAction implements Runnable {

    private final Game game = Game.istance;
    private final States state;

    public ClickAction(Main.States state) {
        this.state = state;
    }

    @Override
    public void run() {
        System.out.printf("Click %s\n", state);
        game.actionListener.performAction(state);
    }
}
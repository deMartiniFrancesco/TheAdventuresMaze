package Action;

import Main.Game;

public record ClickAction(Main.States state) implements Runnable {

    @Override
    public void run() {
        System.out.printf("Click %s\n", state);
        Game.istance.changeState(state);
    }
}
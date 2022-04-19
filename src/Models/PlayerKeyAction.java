package Models;

import javax.swing.*;
import java.awt.event.KeyEvent;

public enum PlayerKeyAction {

    TOP(0, KeyStroke.getKeyStroke(KeyEvent.VK_W, 0)),
    RIGHT(1, KeyStroke.getKeyStroke(KeyEvent.VK_D, 0)),
    BOTTOM(2, KeyStroke.getKeyStroke(KeyEvent.VK_S, 0)),
    LEFT(3, KeyStroke.getKeyStroke(KeyEvent.VK_A, 0)),
    RESET(-1, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0)),
    PAUSE(-1, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

    private final int directionIndex;
    private final KeyStroke key;

    PlayerKeyAction(int index, KeyStroke key) {
        directionIndex = index;
        this.key = key;
    }

    public int getDirectionIndex() {
        return directionIndex;
    }

    public KeyStroke getKey() {
        return key;
    }
}

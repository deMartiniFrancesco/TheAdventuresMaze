package Rpgmaker.Tools;

import javax.swing.*;
import java.awt.*;

public class CursorManager {
    static JFrame frame;

    public static void init(JFrame frame_param) {
        frame = frame_param;
    }

    public static void setCursor(int cursorType) {
        frame.setCursor(Cursor.getPredefinedCursor(cursorType));
    }
}

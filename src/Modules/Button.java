package Modules;

import javax.swing.*;
import Action.ClickAction;
import Interfaces.Action;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    Action action;

    public Button(ImageIcon image, Action action) {

        super(image);
        this.action = action;

        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ignored) {
                action.run();
            }
        });


    }
}

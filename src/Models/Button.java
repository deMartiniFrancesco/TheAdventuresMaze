package Models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    Runnable action;

    public Button(String title, ImageIcon image, Runnable action, int fontSize) {

        super(title, image);
        this.action = action;

        setFont(new Font("Impact", Font.BOLD, fontSize));

        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.run();
    }
}

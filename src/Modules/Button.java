package Modules;

import Interfaces.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    Action action;

    public Button(String title, ImageIcon image, Action action) {

        super(title, image);
        this.action = action;

        setFont(new Font("Impact", Font.BOLD, 40));

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

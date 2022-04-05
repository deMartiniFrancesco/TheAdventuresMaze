package Rpgmaker.Engine;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dialog extends JPanel {
    JLabel label;
    JLabel name;

    public Dialog() {
        label = new JLabel();
        name = new JLabel();
        this.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(6, 6, 6, 6))));
        this.add(name);
        Font f = name.getFont();
        name.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        this.add(label);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        label.setVisible(aFlag);
    }

    public void setText(String s) {
        label.setText(s);
    }
    public void setName(String s) {
        name.setText(s + " :");
    }
}

package Rpgmaker.Engine;

import Rpgmaker.Model.World.Foreground;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Inventory extends JPanel {
    public Inventory() {
        this.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(6, 6, 6, 6))));
    }

    public void updateInventory() {
        this.removeAll();
        this.add(new JLabel("Inventory :\n"));
        for (Foreground fore : EngineState.getInstance().player.getItems()) {
            if (!fore.isPickable)
                continue;
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(fore.getTile().get()));
            this.add(label);
        }
    }
}

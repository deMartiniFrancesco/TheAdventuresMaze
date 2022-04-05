package Rpgmaker.Editor;

import Rpgmaker.Model.World.NPC;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class ToolNPCPanel extends JPanel {
    JLabel Name = new JLabel();
    JLabel Xcoord = new JLabel();
    JLabel Ycoord = new JLabel();
    public JTextArea message = new JTextArea();
    public JCheckBox isMoving = new JCheckBox("Is moving");
    public JButton deleteMe = new JButton("Delete");

    public ToolNPCPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5,1));
        grid.add(Name);
        grid.add(Xcoord);
        grid.add(Ycoord);
        grid.add(isMoving);
        grid.add(deleteMe);
        grid.setVisible(true);
        this.add(grid);
        this.add(message);

    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        Name.setVisible(aFlag);
        Xcoord.setVisible(aFlag);
        Ycoord.setVisible(aFlag);
        message.setVisible(aFlag);
    }

    public void updateInfo(NPC npc) {
        Point2D.Double coord = npc.getPoint();
        Name.setText("Name :" + npc.getName());
        Xcoord.setText("Coord X: " + coord.getX());
        Ycoord.setText("Coord Y: " + coord.getY());
        isMoving.setSelected(npc.isMoving());
        message.setText(npc.getMessage());
    }
}

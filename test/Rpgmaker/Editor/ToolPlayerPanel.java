package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Observable;

public class ToolPlayerPanel extends JPanel {
    JLabel mapName = new JLabel();
    JLabel Xcoord = new JLabel();
    JLabel Ycoord = new JLabel();
    JLabel playerAnim = new JLabel();
    public JButton setSpawn = new JButton("Set Spawn point");

    public ToolPlayerPanel() {
        this.setLayout(new GridLayout(5,1));

        this.add(mapName);
        this.add(Xcoord);
        this.add(Ycoord);
        this.add(playerAnim);
        this.add(setSpawn);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        mapName.setVisible(aFlag);
        Xcoord.setVisible(aFlag);
        Ycoord.setVisible(aFlag);
        playerAnim.setVisible(aFlag);
    }

    public void updateInfo(Player p) {
        Point2D coord = p.getPosition();
        Map map = EditorState.getInstance().world.getMapById(p.getMapId());
        if (map == null)
            mapName.setText("Map : null");
        else
            mapName.setText("Map :" + map.toString());
        Xcoord.setText("Coord X: " + coord.getX());
        Ycoord.setText("Coord Y: " + coord.getY());
        if (p.getAnim() != null)
            playerAnim.setIcon(new ImageIcon(p.getAnim().get()));
    }
}

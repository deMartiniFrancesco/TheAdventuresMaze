package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.Player;
import Rpgmaker.Model.World.Teleporter;

import javax.swing.*;
import java.awt.*;

public class ToolTeleporterPanel extends JPanel {
    public JLabel mapDest;
    public JLabel pointX;
    public JLabel pointY;
    public JLabel pointDestX;
    public JLabel pointDestY;
    public JButton setDestButton;
    public JButton deleteMe;

    public ToolTeleporterPanel() {
        this.setLayout(new GridLayout(7,1));

        this.pointX = new JLabel("X: null");
        this.pointY = new JLabel("Y: null");
        this.mapDest = new JLabel("Destination map: null");
        this.pointDestX = new JLabel("Dest X: null");
        this.pointDestY = new JLabel("Dest Y: null");
        this.setDestButton = new JButton("Set destination");
        this.deleteMe = new JButton("Delete");

        this.add(pointX);
        this.add(pointY);
        this.add(mapDest);
        this.add(pointDestX);
        this.add(pointDestY);
        this.add(setDestButton);
        this.add(deleteMe);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        this.setDestButton.setVisible(aFlag);
        this.mapDest.setVisible(aFlag);
        this.pointDestX.setVisible(aFlag);
        this.pointDestY.setVisible(aFlag);
    }

    public void update(Teleporter t) {
        int mapId = t.getMapDestId();
        if (mapId != -1)
            mapDest.setText("Destination map: " + EditorState.getInstance().world.getMapById(mapId).toString());
        else
            mapDest.setText("Destination map: null");
        Point pt = t.getPosition();
        pointX.setText("X: " + pt.x);
        pointY.setText("Y: " + pt.y);
        Point point = t.getPointDest();
        if (point != null) {
            pointDestX.setText("X: " + point.x);
            pointDestY.setText("Y: " + point.y);
        } else {
            pointDestX.setText("Dest X: null");
            pointDestY.setText("Dest Y: null");
        }
    }
}

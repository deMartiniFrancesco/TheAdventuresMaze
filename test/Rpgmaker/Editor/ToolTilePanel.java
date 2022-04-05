package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.TileType;
import Rpgmaker.Model.Editor.TilesState;
import Rpgmaker.Model.World.BigTile;
import Rpgmaker.Model.World.ImportedTile;
import Rpgmaker.Model.World.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class ToolTilePanel extends JPanel implements Observer {
    public JCheckBox walkCheckbox;
    public JButton bigTileAll;
    public JButton setAsBackground;
    public JButton setPlayerAnim;
    public Vector<JButton> bigTileSeclector = new Vector<>();

    public ToolTilePanel() {
        this.setBackground(Color.MAGENTA);
        this.setLayout(new GridBagLayout());
    }

    public void setUp(Tile tile) {
        removeAll();
        bigTileSeclector.clear();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        add(new JLabel("Selected tile :"), c);
        var label = new JLabel();
        label.setIcon(new ImageIcon(tile.get()));
        c.gridx += 1;
        add(label, c);
        label.setVisible(true);

        c.gridx = 0;
        c.gridy += 1;
        walkCheckbox = new JCheckBox("Set walkable");
        walkCheckbox.setSelected(tile.isDefaultWalkable());
        add(walkCheckbox, c);
        walkCheckbox.setVisible(true);

        setAsBackground = new JButton("Set as background");
        if (tile.getType() == TileType.BACKGROUND) {
            c.gridx = 0;
            c.gridy += 1;
            add(setAsBackground, c);
        }

        setPlayerAnim = new JButton("Set player animation");
        if (tile.getType() == TileType.NPC) {
            c.gridx = 0;
            c.gridy += 1;
            add(setPlayerAnim, c);
        }

        bigTileAll = new JButton("Select full Tile");
        if (tile instanceof BigTile) {
            BigTile bt = (BigTile) tile;
            JPanel container = new JPanel(new GridLayout(bt.getHeight(), bt.getWidth(), 1, 1));
            for (int j = 0; j < bt.getHeight(); j++) {
                for (int i = 0; i < bt.getWidth(); i++) {
                    JButton but = new JButton();
                    but.setIcon(new ImageIcon(bt.getTile(i, j).get()));
                    but.setPreferredSize(new Dimension(16, 16));
                    but.setContentAreaFilled(false);
                    container.add(but);
                    bigTileSeclector.add(but);
                }
            }
            c.gridx = 0;
            c.gridy += 1;
            c.gridwidth = 2;
            add(bigTileAll, c);
            c.gridy += 1;
            add(container, c);
            container.setVisible(true);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TilesState && o instanceof String) {
            String str = (String) o;
            TilesState obj = (TilesState) observable;
            if (str.equals("Change current tile")) {
                setUp(obj.currentTile);
            }
            Editor.validateAll(this);
        }
    }
}

package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.World.Foreground;
import Rpgmaker.Model.World.ImportedTile;
import Rpgmaker.Model.World.NPC;
import Rpgmaker.Model.World.Tile;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Function;

public class ToolForegroundPanel extends JPanel {

    public JLabel image;
    public JLabel name;

    public JCheckBox setBreakable;
    public JCheckBox setAsHide;
    public JCheckBox setPickable;
    public JComboBox<String> breaker;
    public JComboBox<NPC> hider;

    public ToolForegroundPanel() {
        this.setLayout(new GridBagLayout());
        image = new JLabel();
        name = new JLabel();
        setBreakable = new JCheckBox("Set breakable");
        setAsHide = new JCheckBox("Is Hide");
        setPickable = new JCheckBox("Set Pickable");
        breaker = new JComboBox<>();
        hider = new JComboBox<>();

        image.setVisible(true);
        name.setVisible(true);
        setBreakable.setVisible(true);
        setAsHide.setVisible(true);
        setPickable.setVisible(true);
        breaker.setVisible(true);
        hider.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(image, c);
        c.gridy += 1;
        this.add(name, c);
        c.gridy += 1;
        this.add(setBreakable, c);
        c.gridy += 1;
        this.add(breaker, c);
        c.gridy += 1;
        this.add(setAsHide, c);
        c.gridy += 1;
        this.add(hider, c);
        c.gridy += 1;
        this.add(setPickable, c);
    }

    public void updateInfo(Foreground fore) {
        image.setIcon(new ImageIcon(fore.get()));
        name.setText(fore.getName());
        setBreakable.setSelected(fore.isBreakable);
        setAsHide.setSelected(fore.isHided);
        setPickable.setSelected(fore.isPickable);
        UpdateBreaker(fore);
        UpdateHider(fore);
    }

    public void UpdateHider(Foreground f) {
        Point pt = f.getPoint();
        if (f.isHided) {
            Vector<Object> tiles = new Vector<>();
            tiles.addAll(EditorState.getInstance().mapState.currentMap.getNpcs());
            tiles.add(0, "------");
            DefaultComboBoxModel model = new DefaultComboBoxModel(tiles);
            hider.setModel(model);
            for (NPC npc : EditorState.getInstance().mapState.currentMap.getNpcs()) {
                if (npc.getRevealForeground().contains(pt)) {
                    hider.setSelectedItem(npc);
                }
            }
        } else {
            for (NPC npc : EditorState.getInstance().mapState.currentMap.getNpcs()) {
                npc.getRevealForeground().remove(pt);
            }
        }
        hider.setVisible(f.isHided);
    }

    public void UpdateBreaker(Foreground f) {
        if (f.isBreakable) {
            Vector<Object> tiles = new Vector(Arrays.asList(EditorState.getInstance().tilesState.foregroundTiles.values().stream()
                        .map((Function<ImportedTile, Object>) importedTile -> importedTile.toString()).toArray()));
            tiles.add(0, "------");
            DefaultComboBoxModel model = new DefaultComboBoxModel(tiles);
            breaker.setModel(model);
            breaker.setSelectedItem(f.getBreaker() == "" ? "------" : f.getBreaker());
        }
        breaker.setVisible(f.isBreakable);
    }
}

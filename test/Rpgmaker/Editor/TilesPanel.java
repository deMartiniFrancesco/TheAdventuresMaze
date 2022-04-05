package Rpgmaker.Editor;

import Rpgmaker.Model.World.Animation;
import Rpgmaker.Model.World.ImportedTile;
import Rpgmaker.Model.Editor.TilesState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class TilesPanel extends JPanel implements Observer {
    public JTabbedPane tilesTypePanel;

    public TilesSelectPanel foregroundTab;
    public TilesSelectPanel backgroundTab;
    public TilesSelectPanel NPCTab;

    public TreePanel treePanel;

    public TilesPanel() {
        this.setBackground(Color.RED);
        this.setLayout(new GridLayout(2, 1));

        foregroundTab = new TilesSelectPanel();
        foregroundTab.setBackground(Color.CYAN);
        backgroundTab = new TilesSelectPanel();
        backgroundTab.setBackground(Color.MAGENTA);
        NPCTab = new TilesSelectPanel();
        NPCTab.setBackground(Color.PINK);

        tilesTypePanel = new JTabbedPane();
        tilesTypePanel.add("Foreground", Editor.addScrollBar(foregroundTab));
        tilesTypePanel.add("Back", Editor.addScrollBar(backgroundTab));
        tilesTypePanel.add("NPC", Editor.addScrollBar(NPCTab));

        treePanel = new TreePanel();

        this.add(tilesTypePanel);
        this.add(Editor.addScrollBar(treePanel));
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        tilesTypePanel.setVisible(aFlag);

        foregroundTab.setVisible(aFlag);
        backgroundTab.setVisible(aFlag);
        NPCTab.setVisible(aFlag);

        treePanel.setVisible(aFlag);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TilesState && o instanceof String) {
            TilesState obj = (TilesState) observable;
            String str = (String) o;

            if (str.equals("Tiles Update")) {
                backgroundTab.clean();
                foregroundTab.clean();
                NPCTab.clean();
                for (String k : obj.backgroundTiles.keySet()) {
                    BufferedImage icon = obj.backgroundTiles.get(k).get();

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(icon));
                    button.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));

                    backgroundTab.addTile(k, button);
                }
                for (String k : obj.foregroundTiles.keySet()) {
                    ImportedTile fore = obj.foregroundTiles.get(k);
                    BufferedImage icon = fore.get();

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(icon));
                    button.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));

                    foregroundTab.addTile(k, button);
                }
                for (String k : obj.npcTile.keySet()) {
                    Animation anim = obj.npcTile.get(k);
                    BufferedImage icon = anim.get();

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(icon));
                    button.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));

                    NPCTab.addTile(k, button);
                }
                tilesTypePanel.setSelectedIndex(1);
                foregroundTab.validate();
                Editor.validateAll(backgroundTab);
            }
        }
    }
}

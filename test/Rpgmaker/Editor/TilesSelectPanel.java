package Rpgmaker.Editor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TilesSelectPanel extends JPanel {
    public Map<String, JButton> buttons = new HashMap<>();
    public JPanel container = new JPanel();
    public JTextArea searchbar = new JTextArea();

    public TilesSelectPanel() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200,1));
        this.add(container, BorderLayout.CENTER);
        this.add(searchbar, BorderLayout.PAGE_END);
    }

    public void addTile(String name, JButton button) {
        button.setContentAreaFilled(false);
        buttons.put(name, button);
        container.add(button);
    }

    public void clean() {
        for (JButton b : buttons.values()) {
            container.remove(b);
        }
        buttons.clear();
    }

    public void filter(String str) {
        for (String s : buttons.keySet()) {
            buttons.get(s).setVisible(s.contains(str));
        }
    }
}

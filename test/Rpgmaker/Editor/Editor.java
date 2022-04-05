package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.MapState;
import Rpgmaker.Tools.CursorManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Editor extends JFrame implements Observer {
    public static Editor editor;

    public JPanel mainPane;

    public TilesPanel tilesPane;
    public MapPanel mapPane;
    public ToolsPanel toolsPane;

    public TopBar topBar;

    public JLabel infos;

    public Editor() {
        this.editor = this;
        CursorManager.init(this);
        this.setSize(1080,720);
        this.setTitle("BibleRPG - Black Edition");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpEditor();
    }


    private void setUpEditor() {
        topBar = new TopBar();

        tilesPane = new TilesPanel();
        mapPane = new MapPanel();

        toolsPane = new ToolsPanel();
        toolsPane.setBackground(Color.GREEN);

        JPanel infosPannel = new JPanel();
        infos = new JLabel("");
        infosPannel.add(infos);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(mapPane);
        JScrollPane scrollFrame = addScrollBar(panel);

        JPanel another = new JPanel();
        another.setLayout(new BorderLayout());
        another.add(scrollFrame, BorderLayout.CENTER);
        another.add(infos, BorderLayout.PAGE_END);

        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(tilesPane, BorderLayout.WEST);
        mainPane.add(another, BorderLayout.CENTER);
        mainPane.add(toolsPane, BorderLayout.EAST);
        mainPane.add(topBar, BorderLayout.NORTH);

        this.setContentPane(mainPane);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static JScrollPane addScrollBar(JComponent panel) {
        JScrollPane scrollFrame = new JScrollPane(panel);
        scrollFrame.getVerticalScrollBar().setUnitIncrement(16);
        scrollFrame.getHorizontalScrollBar().setUnitIncrement(16);
        return scrollFrame;
    }

    public static void validateAll(Container j) {
        while (j != null) {
            j.validate();
            j = j.getParent();
        }
    }

    @Override
    public void setVisible(boolean flag) {
        mapPane.setVisible(flag);

        tilesPane.setVisible(flag);
        topBar.setVisible(flag);

        mainPane.setVisible(flag);
        super.setVisible(flag);
    }

    public void update(Observable observable, Object o) {
        if (o instanceof String && observable instanceof EditorState){
            String arg = (String) o;
            EditorState obs = (EditorState) observable;
            if (arg.equals("New World") || arg.equals("New Map")) {
                tilesPane.treePanel.show_world(obs.world);
            }
            else if (arg.equals("Time Cycle Update")) {
                toolsPane.toolBoxPanel.showCycleSetting(obs.world.timeCycle);
            }
        }
        else if (o instanceof String && observable instanceof MapState) {
            String arg = (String) o;
            MapState obs = (MapState) observable;
            if (arg.equals("Update Map")) {
                tilesPane.treePanel.update_map(obs.currentMap);
            }
            else if (arg.equals("Set Teleporter Dest")) {
                toolsPane.toolTeleporterPanel.update(obs.getCurrentTeleporter());
            }
            else if (arg.equals("mouseOver") || arg.equals("mousePreview")) {
                if (obs.selectionIn != null && obs.selectionOut != null)
                    infos.setText("Selected : x=" + obs.selectionIn.x + " y=" + obs.selectionIn.y + " => x=" + obs.selectionOut.x + " y=" + obs.selectionOut.y);
                else if (obs.mousePos != null)
                    infos.setText("Mouse : x=" + obs.mousePos.x + " y=" + obs.mousePos.y);
                else
                    infos.setText("Mouse : not in map");
            }
        }
    }

    public static JButton initIconButton(String path, JPanel panel) {
        JButton button =  new JButton();
        try {
            Image img = ImageIO.read(ClassLoader.getSystemResource(path));

            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (panel != null)
            panel.add(button);
        button.setPreferredSize(new Dimension(42,42));
        return button;
    }

    public static Editor getInstance() {
        return editor;
    }
}

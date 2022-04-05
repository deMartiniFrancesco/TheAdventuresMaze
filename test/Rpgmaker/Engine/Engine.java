package Rpgmaker.Engine;

import Rpgmaker.Model.World.Music;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Engine extends JFrame implements Observer {
    public Display mapPanel;
    public Music music;

    public static Engine engine;

    public Engine() {
        engine = this;
        this.setSize(1080,720);
        this.setTitle("BibleEngine");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setLocationRelativeTo(null);

        mapPanel = new Display();
        mapPanel.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(mapPanel);
        panel.setVisible(true);


        JPanel level = new JPanel();
        level.setLayout(new BorderLayout());
        level.add(panel, BorderLayout.CENTER);
        level.setVisible(true);

        this.add(level);

        this.setVisible(true);
        System.out.println("FRAME: " + this.getWidth() + " " + this.getHeight());
        System.out.println("\tLEVEL: " + level.getWidth() + " " + level.getHeight());
        System.out.println("\t\tPANEL: " + panel.getWidth() + " " + panel.getHeight());
        System.out.println("\t\t\tMAP: " + mapPanel.getWidth() + " " + mapPanel.getHeight());
    }

    public static void validateAll(Container j) {
        while (j != null) {
            j.validate();
            j = j.getParent();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EngineState) {
            if (arg instanceof String) {
                String s = (String) arg;
                if (s.equals("Switch Time")) {
                    mapPanel.drawTimeCycleLayer(EngineState.getInstance().currentMap,
                                                EngineState.getInstance().world.timeCycle.isNight());
                } else if (s.equals("Update foreground")) {
                    mapPanel.drawForeLayer(EngineState.getInstance().currentMap);
                } else if (s.equals("Change Map")) {
                    Music  music = EngineState.getInstance().currentMap.getMusic();
                    try {
                        System.out.println(Music.musicPlayed + "END");
                        if (music != null) {
                            music.play();
                            System.out.println("OK");
                        } else {
                            Music.stop();
                            System.out.println("DONE");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Engine getInstance() {
        return engine;
    }
}

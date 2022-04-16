package Main;

import Graffica.GridPane;
import Graffica.MenuPanel;
import Interfaces.Application;
import Modules.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game implements Application {

    public static Game istance;
    private static String projectPath;
    public Random random = new Random();
    public States state;

    public static int level = 10;

    public Grid grid;
    public JPanel mainPane;
    public JFrame frame;


    public Game(String projectPath) {
        Game.projectPath = projectPath;
    }

    @Override
    public String getResources() {
        return projectPath + "/Resources/";
    }

    @Override
    public void onDataLoad() {

    }

    @Override
    public void onEnable() {
        istance = this;

        System.out.printf("projectPath %s\n", projectPath);



        changeState(States.MENU);
    }

    public void changeState(States state) {
        this.state = state;
        switch (state) {
            case PLAING -> {
                grid.addPlayer();
                mainPane.repaint();
            }
            case STARTING -> {
                if (frame != null) {
                    frame.dispose();
                }
                frame = new JFrame("Maze");
                grid = new Grid(this);
                mainPane = new GridPane(this);
                grid.generateMaze();
                draw();
            }
            case FINISH -> {
                level++;
                changeState(States.STARTING);
            }
            case MENU -> {
                if (frame != null) {
                    frame.dispose();
                }
                frame = new JFrame("Menu");
                mainPane = new MenuPanel();
                draw();
            }
        }
    }

    public void draw() {

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ignored) {
            }
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(mainPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    @Override
    public void onDisable() {
    }


}

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

    public static int level = 20;

    public boolean isPause = false;


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

        changeState(States.MENU);
    }

    public void changeState(States state) {
        this.state = state;
        switch (state) {
            case MENU -> {
                initFrame();
                mainPane = new MenuPanel();
                draw();
            }
            case STARTING -> {
                initFrame();
                grid = new Grid(this);
                mainPane = new GridPane(this);
                grid.generateMaze();
                draw();
            }
            case PLAING -> {

                /* TODO
                    start timer
                    unblind maze
                 */

                if (grid.getPlayer() == null) {
                    grid.addPlayer();
                }
                mainPane.repaint();
            }
            case PAUSE -> {

                /* TODO
                    show pause menu
                    pause the timer
                    blind maze
                */

            }


            case FINISH -> {
                /* TODO
                    stop timer
                    save on file
                 */
                level++;
                changeState(States.STARTING);
            }
        }
    }

    private void initFrame() {
        if (frame != null) {
            frame.dispose();
        }
        frame = new JFrame("TheAdventuresMaze");
    }

    public void setPause() {
        isPause = !isPause;

        if (isPause) {
            changeState(States.PAUSE);
        } else {
            changeState(States.PLAING);
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

package Main;

import Control.Timer;
import Graffica.GridPane;
import Graffica.MainFrame;
import Graffica.MenuPanel;
import Control.Interfaces.Application;
import Models.Chronometer;
import Models.Grid;
import Models.PlayerKeyAction;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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
    public MainFrame frame;

    public Chronometer chronometer;


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

        for (States states : PlayerKeyAction.values()) {
            motion.addAction(List.of(action.getKey(), action, this));
        }
    }


    public void initFrame() {
        if (frame != null) {
            frame.dispose();
        }
        frame = new MainFrame("TheAdventuresMaze");
    }

    public void setPause() {
        isPause = !isPause;

        if (isPause) {
            changeState(States.PAUSE);
        } else {
            changeState(States.PLAING);
        }
    }


    @Override
    public void onDisable() {



        System.exit(0);
    }
}

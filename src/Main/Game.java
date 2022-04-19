package Main;

import Action.Listener.GameActionListener;
import Grafica.MainFrame;
import Control.Interfaces.Application;
import Models.Chronometer;
import Models.Grid;

import javax.swing.*;
import java.util.Random;

public class Game implements Application {

    public static Game istance;
    private static String projectPath;
    public Random random = new Random();
    public States state;

    public GameActionListener actionListener;
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

        actionListener = new GameActionListener();

        actionListener.performAction(States.MENU);
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
            actionListener.performAction(States.PAUSE);
        } else {
            actionListener.performAction(States.PLAING);
        }
    }


    @Override
    public void onDisable() {



        System.exit(0);
    }
}

package Main;

import Action.Listener.GameActionListener;
import Control.Interfaces.Application;
import Grafica.MainFrame;
import Models.Chronometer;
import Models.Grid;
import Saving.SaveJson;
import Saving.SaveObject;
import Saving.TimeLevel;

import javax.swing.*;
import java.io.IOException;
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
        istance = this;

        actionListener = new GameActionListener();

    }

    @Override
    public void onEnable() {
        actionListener.performAction(States.MENU);
    }

    public void clearFrame() {
        if (frame != null) {
            frame.remove(mainPane);
        } else {
            frame = new MainFrame("TheAdventuresMaze");
        }
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

    public void onTest() {
        SaveJson saveJson = new SaveJson();

        saveJson.saveOnFile(new SaveObject(
                "Prova",
                new TimeLevel[]{
                        new TimeLevel(1, 5000),
                        new TimeLevel(2, 8000)
                }
        ));

        try {
            System.out.println(saveJson.getObjectFromJsonFile(getResources() + "save.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

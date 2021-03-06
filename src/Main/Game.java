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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
            JPanel contentPane = (JPanel) frame.getContentPane();
            contentPane.removeAll();
        } else {
            frame = new MainFrame("TheAdventuresMaze");
        }
    }


    @Override
    public void onDisable() {
        System.exit(0);
    }

    public void onTest() {
        SaveJson saveJson = new SaveJson();

        SaveObject playerSaveObject2 = new SaveObject("Player 1",
                new LinkedHashSet<>(
                        Set.of(
                                new TimeLevel(3, 2000)
                        )
                )
        );

        saveJson.saveObject(playerSaveObject2);

    }
}

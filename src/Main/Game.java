package Main;

import Graffica.GridPane;
import Interfaces.Application;
import Modules.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game implements Application {

    public static Game istance;
    public Random random = new Random();
    public States state;

    public static int level = 10;

    public Grid grid;
    public GridPane gridPane;
    public JFrame frame;


    @Override
    public void onDataLoad() {

    }

    @Override
    public void onEnable() {
        istance = this;


        grid = new Grid(this);
        gridPane = new GridPane(this);

        changeState(States.LOADING);
    }

    public void changeState(States state) {
        this.state = state;
        switch (state) {
            case PLAING -> {
                grid.addPlayer();
                gridPane.repaint();
            }
            case LOADING -> {
                grid.generateMaze();
                draw();
            }
            case FINISH -> {
                frame.dispose();
                level++;
                grid = new Grid(this);
                gridPane = new GridPane(this);
                changeState(States.LOADING);
            }
        }
    }

    public void draw() {
        frame = new JFrame("Maze");
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ignored) {
            }
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(gridPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    @Override
    public void onDisable() {
    }


}

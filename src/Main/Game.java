package Main;

import Graffica.GridPane;
import Interfaces.Application;
import Modules.Grid;
import Providers.CoordsProvider;

import javax.swing.*;
import java.awt.*;

public class Game implements Application {

    public static Game istance;

    public States state;

    public Grid grid;

    private GridPane gridPane;
    private CoordsProvider coordsProvider;

    public CoordsProvider getCoordsProvider() {
        return coordsProvider;
    }


    @Override
    public void onDataLoad() {

    }

    @Override
    public void onEnable() {
        istance = this;

        //Init Providers
        coordsProvider = new CoordsProvider(this);


        grid = new Grid(this);
        gridPane = new GridPane(this, grid);
        draw();

        changeState(States.LOADING);
    }

    public void changeState(States state){
        this.state = state;
        switch (state){
            case PLAING -> {
                grid.addPlayer();
                gridPane.repaint();
            }
            case LOADING -> {
                grid.generateMaze();
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

            JFrame frame = new JFrame("Testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

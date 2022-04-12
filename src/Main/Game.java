package Main;

import Graffica.TestPane;
import Interfaces.Application;
import Modules.Grid;
import Providers.CoordsProvider;

import javax.swing.*;
import java.awt.*;

public class Game implements Application {

    public static Game istance;
    public static Grid grid;
    private CoordsProvider coordsProvider;

    public CoordsProvider getCoordsProvider() {
        return coordsProvider;
    }


    @Override
    public void onDataLoad() {

    }

    @Override
    public void onEnable(){
        istance = this;

        //Init Providers
        coordsProvider = new CoordsProvider(this);



        grid = new Grid(this);



        draw();
    }

    public void draw(){
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }

            JFrame frame = new JFrame("Testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new TestPane(grid.getLenCampo()));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }



    @Override
    public void onDisable(){}



}

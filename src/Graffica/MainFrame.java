package Graffica;

import Main.Game;
import Main.States;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class MainFrame extends JFrame {

    private Runnable operation;
    private final Game game = Game.istance;

    public MainFrame(String title) throws HeadlessException {
        super(title);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (operation != null) {
                    operation.run();
                }
                dispose();
            }
        });

    }
    public void setCloseOperation(Runnable operation) {
        this.operation = operation;
    }


    public void draw(){
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ignored) {
            }
            setCloseOperation(()-> changeState(States.FINISH));
            setLayout(new BorderLayout());
            add(mainPane);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }



}

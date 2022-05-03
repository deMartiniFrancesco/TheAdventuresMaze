package Grafica;

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

        draw();

    }

    public void setCloseOperation(Runnable operation) {
        this.operation = operation;
    }


    public void draw() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException exception) {
                exception.printStackTrace();
            }
            setCloseOperation(() -> game.actionListener.performAction(States.FINISH));
            setLayout(new BorderLayout());
            setResizable(false);
            pack();
            setVisible(true);
        });
    }


    public void addComponent(JPanel panel) {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(panel);
        pack();
        setLocationRelativeTo(null);
        contentPane.repaint();
        contentPane.revalidate();
    }
}

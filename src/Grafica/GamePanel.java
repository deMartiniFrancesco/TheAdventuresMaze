package Grafica;

import Action.ClickAction;
import Action.Listener.MotionActionListener;
import Control.Interfaces.MovableEntity;
import Control.Interfaces.WindowPanel;
import Control.Timer;
import Main.Game;
import Main.States;
import Models.Button;
import Models.Chronometer;
import Models.ChronometerLabel;
import Models.PlayerKeyAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GamePanel extends JPanel implements WindowPanel {
    private final Game game = Game.istance;

    private Image pauseIcon;

    public GamePanel() {

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        try {
            pauseIcon = ImageIO
                    .read(new File(Game.istance.getResources() + "pause_icon.png"))
                    .getScaledInstance(30, 30, Image.SCALE_DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new Button("", new ImageIcon(pauseIcon), new ClickAction(States.PAUSE), 0), constraints);

        ChronometerLabel time = new ChronometerLabel();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.7;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(time, constraints);

        game.chronometer = new Chronometer(10, Timer.DURATION_INFINITY, time);
        game.chronometer.start();


        GridPane gridPane = new GridPane();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(gridPane, constraints);

    }


    @Override
    public void addAction(List<?> args) {
        KeyStroke pressedKeyStroke;
        PlayerKeyAction direction;
        MovableEntity target;
        try {
            pressedKeyStroke = (KeyStroke) args.get(0);
            direction = (PlayerKeyAction) args.get(1);
            target = (MovableEntity) args.get(2);
        } catch (Exception ignored) {
            return;
        }

        String name = pressedKeyStroke.toString();

        MotionActionListener action = new MotionActionListener(name, direction, target);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        getActionMap().put(name, action);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

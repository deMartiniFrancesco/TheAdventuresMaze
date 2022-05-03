package Grafica;

import Action.ClickAction;
import Action.Listener.MotionActionListener;
import Control.Interfaces.MovableEntity;
import Control.Interfaces.WindowPanel;
import Main.Game;
import Main.States;
import Models.Button;
import Models.PlayerKeyAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GamePanel extends JPanel implements WindowPanel {

    private final Game game = Game.istance;
    private final GridPane gridPane;

    public GamePanel() {


        gridPane = new GridPane();

        try {
            Image pauseIcon = ImageIO
                    .read(new File(Game.istance.getResources() + "pause_icon.png"))
                    .getScaledInstance(30, 30, Image.SCALE_DEFAULT);

            add(new Button(
                    "",
                    new ImageIcon(pauseIcon),
                    new ClickAction(States.PAUSE),
                    0
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }


        JLabel time = new JLabel("...");
        add(time);


        add(gridPane);
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

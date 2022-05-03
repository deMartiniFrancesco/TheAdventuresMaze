package Grafica;

import Action.Listener.MotionActionListener;
import Control.Interfaces.MovableEntity;
import Control.Interfaces.WindowPanel;
import Main.Game;
import Main.States;
import Models.Button;
import Action.ClickAction;
import Models.PlayerKeyAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

// JPanel shown in the modal JDialog above
public class PausePane extends JPanel implements WindowPanel {

    private final int panelWidth = 250;
    private final int panelHeight = 250;


    private static final Color BG = new Color(123, 63, 0);

    public PausePane() {
        try {
            Image buttonIcon = ImageIO
                    .read(new File(Game.istance.getResources() + "button.png"))
                    .getScaledInstance(panelWidth - 5, panelHeight / 3, Image.SCALE_DEFAULT);


            JLabel pausedLabel = new JLabel("PAUSED");
            pausedLabel.setForeground(Color.ORANGE);
            JPanel pausedPanel = new JPanel();
            pausedPanel.setOpaque(false);
            pausedPanel.add(pausedLabel);

            setBackground(BG);
            int eb = 15;
            int fontSize = 30;
            setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
            setLayout(new GridLayout(0, 1, 10, 10));
            add(pausedPanel);
            add(new Button(
                    "Continue",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.PAUSE),
                    fontSize
            ));
            add(new Button(
                    "Restart",
                    new ImageIcon(buttonIcon),
                    new ClickAction(List.of(States.PAUSE, States.STARTING)),
                    fontSize
            ));
            add(new Button(
                    "Menu",
                    new ImageIcon(buttonIcon),
                    new ClickAction(List.of(States.PAUSE, States.MENU)),
                    fontSize
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    public void addAction(List<?> args) {
        KeyStroke pressedKeyStroke;
        PlayerKeyAction direction;
        Object target;
        try {
            pressedKeyStroke = (KeyStroke) args.get(0);
            direction = (PlayerKeyAction) args.get(1);
            target = args.get(2);
        } catch (Exception ignored) {
            return;
        }

        String name = pressedKeyStroke.toString();

        MotionActionListener action = new MotionActionListener(name, direction, target);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        getActionMap().put(name, action);

    }
}
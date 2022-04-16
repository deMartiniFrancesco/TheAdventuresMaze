package Graffica;

import Interfaces.WindowPanel;
import Action.ClickAction;
import Main.Game;
import Main.States;
import Modules.Button;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuPanel extends JPanel implements WindowPanel {

    private Image image;
    private final int panelWidth = 400;
    private final int panelHeight = 600;

    public MenuPanel() {
        try {
            image = ImageIO.read(new File(Game.istance.getResources() + "menu_bg.png")).getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);

            Image buttonIcon = ImageIO
                    .read(new File(Game.istance.getResources() + "button.png"))
                    .getScaledInstance(panelWidth - 50, panelHeight / 6, Image.SCALE_DEFAULT);


            Button startButton = new Button(
                    "Start",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.STARTING)
            );
            this.add(startButton);

            Button settingsButton = new Button(
                    "Settings",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.STARTING)
            );
            this.add(settingsButton);

            JButton scoresButton = new JButton(new ImageIcon(buttonIcon));
            scoresButton.setBorder(BorderFactory.createEmptyBorder());
            scoresButton.setContentAreaFilled(false);
            this.add(scoresButton);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

    @Override
    public void addAction(List<?> args) {
        JButton target;
        States state;
    }
}
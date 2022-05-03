package Grafica;

import Action.ClickAction;
import Control.Interfaces.WindowPanel;
import Main.Game;
import Main.States;
import Models.Button;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MenuPanel extends JPanel implements WindowPanel {

    private Image image;
    private final int panelWidth = 400;
    private final int panelHeight = 450;

    public MenuPanel() {
        try {
            image = ImageIO
                    .read(new File(Game.istance.getResources() + "menu_bg.png"))
                    .getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);

            Image buttonIcon = ImageIO
                    .read(new File(Game.istance.getResources() + "button.png"))
                    .getScaledInstance(panelWidth - 50, panelHeight / 5, Image.SCALE_DEFAULT);

            setLayout(new GridLayout(4, 0, 10, 0));

            JLabel title = new JLabel("TheAdventureMaze");
            title.setHorizontalAlignment( SwingConstants.CENTER );
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Impact", Font.PLAIN, 30));

            add(title, 0, 0);

            Button startButton = new Button(
                    "Start",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.STARTING),
                    40
            );
            this.add(startButton);

            Button settingsButton = new Button(
                    "Settings",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.SETTINGS),
                    40
            );
            this.add(settingsButton);

            Button scoresButton = new Button(
                    "Scores",
                    new ImageIcon(buttonIcon),
                    new ClickAction(States.SCORES),
                    40
            );
            this.add(scoresButton);

        } catch (IOException ex) {
            ex.printStackTrace();
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
    }
}
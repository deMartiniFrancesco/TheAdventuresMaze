package Grafica;

import Control.Interfaces.WindowPanel;
import Main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SettigsPanel extends JPanel implements WindowPanel {
    private final int panelWidth = 400;
    private final int panelHeight = 500;

    private Image image;

    private JLabel name;
    private JTextField tname;

    public SettigsPanel() {
        try {
            image = ImageIO
                    .read(new File(Game.istance.getResources() + "menu_bg.png"))
                    .getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        add(tname);


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

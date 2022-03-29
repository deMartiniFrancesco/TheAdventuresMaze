package Sokoban;

import javax.swing.*;
import java.awt.*;

public class Wall extends Actor {

    private Image image;

    public Wall(int x, int y) {
        super(x, y);

        initWall();
    }

    private void initWall() {

        ImageIcon iicon = new ImageIcon("./test/Sokoban/resurces/wall.png");
        image = iicon.getImage();
        setImage(image);
    }
}
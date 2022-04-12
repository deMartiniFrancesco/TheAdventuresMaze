package Sokoban;

import javax.swing.*;
import java.awt.*;

public class Wall extends Actor {

    public Wall(int x, int y) {
        super(x, y);

        initWall();
    }

    private void initWall() {

        ImageIcon iicon = new ImageIcon("./test/Sokoban/resurces/wall.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
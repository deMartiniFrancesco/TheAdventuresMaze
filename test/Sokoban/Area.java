package Sokoban;

import javax.swing.*;
import java.awt.*;

public class Area extends Actor {

    public Area(int x, int y) {
        super(x, y);

        initArea();
    }

    private void initArea() {

        ImageIcon iicon = new ImageIcon("./test/Sokoban/resurces/area.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
package Sokoban;

import javax.swing.*;
import java.awt.*;

public class Baggage extends Actor {

    public Baggage(int x, int y) {
        super(x, y);

        initBaggage();
    }

    private void initBaggage() {

        ImageIcon iicon = new ImageIcon("./test/Sokoban/resurces/baggage.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {

        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }
}
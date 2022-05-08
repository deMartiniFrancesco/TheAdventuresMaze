package Models;

import Main.Game;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ChronometerLabel extends JLabel {

    public ChronometerLabel() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Fixedsys", Font.PLAIN, 30));
    }

    public void updateText(long elapsedTime) {
        setText(format(elapsedTime));
        repaint();
    }

    private String format(long millis) {
        long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        long mill = millis % 1000;

        return String.format(
                "%02d : %02d : %03d",
                min,
                sec,
                mill
        );
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Test {

    private enum Directions {

        TOP(0),
        LEFT(1),
        RIGHT(2),
        DOWN(3);

        private final int index;

        Directions(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }


    private record GameInput(List<Boolean> directionBooleanList) implements KeyListener {

        private void printInputList() {
            System.out.println(directionBooleanList);
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S -> directionBooleanList.set(Directions.DOWN.getIndex(), false);
                case KeyEvent.VK_W -> directionBooleanList.set(Directions.TOP.getIndex(), false);
                case KeyEvent.VK_D -> directionBooleanList.set(Directions.RIGHT.getIndex(), false);
                case KeyEvent.VK_A -> directionBooleanList.set(Directions.LEFT.getIndex(), false);
                default -> {
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S -> directionBooleanList.set(Directions.DOWN.getIndex(), true);
                case KeyEvent.VK_W -> directionBooleanList.set(Directions.TOP.getIndex(), true);
                case KeyEvent.VK_D -> directionBooleanList.set(Directions.RIGHT.getIndex(), true);
                case KeyEvent.VK_A -> directionBooleanList.set(Directions.LEFT.getIndex(), true);
                default -> {
                    return;
                }
            }
            System.out.println("Key press");
            printInputList();
        }
    }

    public static void main(String[] args) {
        final List<Boolean> directionBooleanList = Collections.synchronizedList(new ArrayList<>(4));
        Image im = new BufferedImage(5, 5, 5);


        for (int i = 0; i < Directions.values().length; i++) {
            directionBooleanList.add(false);
        }

        final JFrame frame = new JFrame();
        getCh(frame, directionBooleanList);

        synchronized (directionBooleanList) {
            for (Boolean aBoolean : directionBooleanList) {
                System.out.println(aBoolean);
            }
        }

        drawCircle(5, 5, im, frame);

    }


    public static void getCh(JFrame frame, List<Boolean> directionBooleanList) {
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        frame.addKeyListener(new GameInput(directionBooleanList));
        frame.setVisible(true);
    }


    public static void drawCircle(int x, int y, Image im, JFrame frame) {
        im.getGraphics().drawOval(x, y, 50, 50); //TODO: should really dispose the Graphics object after drawing the oval
        frame.repaint();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GameInput implements KeyListener {

    private final List<Boolean> directionBooleanList;
    private final DrawMyCercle parent;
    private final int movmentSpeed = 20;

    GameInput(DrawMyCercle parent) {
        this.parent = parent;
        this.directionBooleanList = parent.getDirectionBooleanList();
    }

    private void printInputList() {
        if(directionBooleanList.get(Directions.DOWN.getIndex())) // Move the player down
            parent.setCircleY(parent.getCircleY() + movmentSpeed);
        if(directionBooleanList.get(Directions.TOP.getIndex())) // Move the player up
            parent.setCircleY(parent.getCircleY() - movmentSpeed);
        if(directionBooleanList.get(Directions.RIGHT.getIndex())) // Move the player to the right
            parent.setCircleX(parent.getCircleX() + movmentSpeed);
        if(directionBooleanList.get(Directions.LEFT.getIndex())) // Move the player to the left
            parent.setCircleX(parent.getCircleX() - movmentSpeed);
        parent.repaint();
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
        printInputList();
    }
}

enum Directions {

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


class DrawMyCercle extends JPanel {

    private int circleX = 250;
    private int circleY = 250;

    private final List<Boolean> directionBooleanList = Collections.synchronizedList(new ArrayList<>(4));

    public DrawMyCercle() {
        for (int i = 0; i < Directions.values().length; i++) {
            directionBooleanList.add(false);
        }
        JFrame f = new JFrame("Draw a circle");
        f.getContentPane().add(this);
        f.setSize(500, 500);
        f.setVisible(true);
        f.setResizable(false);
        f.addKeyListener(new GameInput(this));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getCircleX() {
        return circleX;
    }

    public void setCircleX(int circleX) {
        this.circleX = circleX;
    }

    public int getCircleY() {
        return circleY;
    }

    public void setCircleY(int circleY) {
        this.circleY = circleY;
    }

    public List<Boolean> getDirectionBooleanList() {
        return directionBooleanList;
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.drawOval(circleX, circleY, 20, 20);
    }

    public static void main(String[] args) {

        DrawMyCercle main = new DrawMyCercle();

    }
}
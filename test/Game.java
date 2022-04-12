import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game {

    private GamePanel gamePanel;

    public Game() {
        createAndShowUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame("Maximiza");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UP pressed");
        gamePanel.getActionMap().put("UP pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.moveUp(); // this would be something like gameObject.moveDown()
            }
        });
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DOWN pressed");
        gamePanel.getActionMap().put("DOWN pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.moveDown(); // this would be something like gameObject.moveDown()
            }
        });

        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);

        // a more approrpiate game loop can be used as we only need to call repaint 60 times per second not more or less.
        // fixed step: https://stackoverflow.com/questions/13739693/java-swing-based-game-framework-any-advice/13740162#13740162
        // variable step: https://github.com/davidkroukamp/SwingGameLibrary/blob/main/src/za/co/swinggamelibrary/GameLoop.java
        Thread gameLoop = new Thread(() -> {
            while (true) {
                gamePanel.repaint();

                // let's sleep a bit not to eat up processor time unnecessarily needs a better implementation of a game loop but thread sleep will do okay for now
                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {
                }
            }
        });
        gameLoop.start();
    }

    public static class GamePanel extends JPanel {

        // these properties should go into its own game object which is separated from the game panel, and can simply be added to the game panel via a list
        // and in the paintComponent method you simply iterate all the game objects and draw them
        // https://github.com/davidkroukamp/SwingGameLibrary-Samples/blob/main/sample1/src/sgltest/Player.java
        public int xPosition = 240;
        public int yPosition = 240;
        public int speed = 30;

        double t = 0.0;
        final double dt = 1.0 / 60.0;  // game updates per second

        double currentTime = System.nanoTime();
        double accumulator = 0.0;

        public int playerSize = 20;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            accumulator += frameTime / 1000000000.0;  // convert to seconds

            while (accumulator >= dt) {
                accumulator -= dt;
                t += dt;
            }


            // can make graphics look better too using some rendering hints https://github.com/davidkroukamp/SwingGameLibrary/blob/main/src/za/co/swinggamelibrary/Graphics2DHelper.java
            // should move to game object inside a render(Graphics2D g2d) method which gets called for each game object passing in the graphics parameter
            g.setColor(Color.yellow);
            g.fillRect(xPosition, yPosition, playerSize, playerSize);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }

        // these methods would go into the game object class too under a single move methid which checks booleans on whether to move up or down or whatever
        public void moveUp() {
            yPosition -= speed * dt; // this could probably just set a boolean which your game object will check and update its position accordingly via a move() method thats called before gamePanel.repaint in the thread
        }

        public void moveDown() {
            yPosition += speed * dt; // this could probably just set a boolean which your game object will check and update its position accordingly via a move() method thats called before gamePanel.repaint in the thread
        }

    }
}
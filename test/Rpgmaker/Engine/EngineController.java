package Rpgmaker.Engine;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Engine.Timer;
import Rpgmaker.Model.World.Foreground;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.Music;
import Rpgmaker.Model.World.World;
import Rpgmaker.Tools.ThreadLauncher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class EngineController {
    public Engine frame;
    public EngineState state;
    Vector<Boolean> keyState = new Vector<>();
    Timer timeCycle = null;

    public EngineController(World world) {
        for (int i = 0; i < 256; i++) {
            keyState.add(false);
        }
        frame = new Engine();
        frame.mapPanel.pause.resume.addActionListener(e -> {
            keyState.set(KeyEvent.VK_P, true);
            frame.requestFocus();
        });
        frame.mapPanel.pause.exit.addActionListener(e -> {
            frame.dispose();
            destroyGame();
        });
        frame.mapPanel.pause.replay.addActionListener(e -> {
            frame.dispose();
            rebootGame(world);
        });
        frame.mapPanel.pause.save.addActionListener(e -> state.saveState());
        frame.mapPanel.pause.load.addActionListener(e -> state.loadState());

        state = new EngineState(world, this);
        state.addObserver(frame.mapPanel);
        state.addObserver(frame);
        state.init();

        if (state.world.timeCycle.isActive()) {
            timeCycle = new Timer(state.world.timeCycle.getDelay() * 1000,
                                         e -> timeCycle.updateDelay(state.switchTime() * 1000));
            timeCycle.start();
            timeCycle.updateDelay(state.world.timeCycle.getNextDelay() * 1000);
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public synchronized void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public synchronized void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() < 256)
                    keyState.set(keyEvent.getKeyCode(), true);
            }

            @Override
            public synchronized void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() < 256)
                    keyState.set(keyEvent.getKeyCode(), false);
            }
        });

        ThreadLauncher.execute(() -> {
            new Game(this);
            frame.dispose();
            destroyGame();
        });


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            destroyGame();
            }
        });
    }

    public void rebootGame(World world) {
        Music.destroy();
        keyState.set(KeyEvent.VK_ESCAPE, true);
        for (Map m : world.getMaps()) {
            for (Foreground f : m.getForegroundSet().values()) {
                f.isRemoved = false;
                f.isShowed = false;
            }
        }
        if (timeCycle != null)
            timeCycle.stop();
        new EngineController(world);
    }

    public void destroyGame() {
        Music.destroy();
        keyState.set(KeyEvent.VK_ESCAPE, true);
        if (!Main.standalone) {
            for (Map m : EditorState.getInstance().world.getMaps()) {
                for (Foreground f : m.getForegroundSet().values()) {
                    f.isRemoved = false;
                    f.isShowed = false;
                }
            }
        }
        if (timeCycle != null)
            timeCycle.stop();
        if (Main.standalone)
            System.exit(0);
    }

    public void pauseGame() {
        System.out.println("Pause...");
        state.setPause(true);

        Music.pause();
        if (timeCycle != null)
            timeCycle.pause();

        while (!keyState.get(KeyEvent.VK_P)) {
            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Music.restart();
        if (timeCycle != null)
            timeCycle.resume();

        state.setPause(false);
        System.out.println("Resume...");
        keyState.set(KeyEvent.VK_P, false);
    }
}
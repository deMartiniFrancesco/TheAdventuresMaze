package Rpgmaker.Model.Editor;

import Rpgmaker.Engine.EngineController;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.World;
import Rpgmaker.Tools.FileManager;
import Rpgmaker.Tools.JarMaker;
import Rpgmaker.Tools.PopUpManager;
import Rpgmaker.Tools.ThreadLauncher;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

public class EditorState extends Observable {
    public static EditorState instance;

    public ToolsState toolsState;
    public TilesState tilesState;
    public MapState mapState;

    public World world;

    public boolean showGrid = true;
    public boolean showWalk = false;

    public EditorState() {
        instance = this;
        toolsState = new ToolsState();
        tilesState = new TilesState();
        mapState = new MapState();
    }

    public static EditorState getInstance() {
        return instance;
    }

    public void getWorld() {
        File file = FileManager.getFile();
        if (file == null)
            return;
        if (world != null && !PopUpManager.Confirm("Do you really want to load this world ?\n" +
                "You will loose your current World."))
            return;
        World backup = world;
        Gson gson = new Gson();
        try {
            world = gson.fromJson(new FileReader(file), World.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        boolean res = world.setUpLoad();
        if (!res) {
            world = backup;
            // Error
            PopUpManager.Alert("Cetaines tiles n'ont pas été trouvés.");
            return;
        }
        mapState.updateMap(world.getMaps().get(0));
        mapState.setPlayer(world.getPlayer());
        this.showGrid = false;
        setChanged();
        notifyObservers("New World");
    }

    public void defaultWorld(Map map) {
        world = new World("New World");
        world.addMap(map);
        mapState.updateMap(world.getMaps().get(0));
        mapState.setPlayer(world.getPlayer());
        setChanged();
        notifyObservers("New World");
    }

    public void addMap(Map map) {
        world.addMap(map);
        setChanged();
        notifyObservers("New Map");
    }

    public void saveWorld() {
        FileManager.saveFile(world, ".wrld");
    }

    public void invertGrid() {
        if (mapState.currentMap == null)
            return;
        showGrid = !showGrid;
        mapState.mousePreview(mapState.selectionIn, mapState.selectionOut);
        mapState.updateMap(true);
    }

    public void renameMap() {
        String s = PopUpManager.askString("Choose a new name for " + mapState.currentMap.toString());
        if (s != null)
            mapState.currentMap.setName(s);
        setChanged();
        notifyObservers("New Map");
    }

    public void renameWorld() {
        String s = PopUpManager.askString("Choose a new name for " + world.toString());
        if (s != null)
            world.setName(s);
        setChanged();
        notifyObservers("New World");
    }

    public void launchGame() {
        if (world == null) {
            PopUpManager.Alert("Please create or load a world before.");
            return;
        }
        if (world.getPlayer().getAnim() == null) {
            PopUpManager.Alert("Please set a player animation before.");
            return;
        }
        ThreadLauncher.execute(() -> new EngineController(world));
    }

    public void reverseTimeCycleSetting() {
        if (world == null)
            return;
        world.timeCycle.reverseActive();
        setChanged();
        notifyObservers("Time Cycle Update");
    }

    public void createJar() {
        if (world == null) {
            PopUpManager.Alert("Please create or load a world before.");
            return;
        }
        if (world.getPlayer().getAnim() == null) {
            PopUpManager.Alert("Please set a player animation before.");
            return;
        }
        ThreadLauncher.execute(() -> JarMaker.makeJar(world));
    }

    public World getWorldJAR() {
        Gson gson = new Gson();
        World w = null;
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("world.json");
            w = gson.fromJson(new InputStreamReader(in), World.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        boolean res = w.setUpLoad();
        System.out.println(res);
        return w;
    }
}

package Rpgmaker.Model.Editor;

import Rpgmaker.Model.World.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TilesState extends Observable {
    public Tile currentTile;

    public Map<String, Tile> backgroundTiles = new HashMap<>();
    public Map<String, ImportedTile> foregroundTiles = new HashMap<>();
    public Map<String, Animation> npcTile = new HashMap<>();
    public Vector<Music> musics = new Vector<>();

    public TilesState() {
    }

    public void setCurrentTile(String currentTile, TileType type) {
        if (type == TileType.FOREGROUND) {
            this.currentTile = foregroundTiles.get(currentTile);
        } else if (type == TileType.NPC) {
            this.currentTile = npcTile.get(currentTile);
        } else {
            this.currentTile = backgroundTiles.get(currentTile);
        }
        if (EditorState.getInstance().mapState.selectionIn != null) {
            EditorState.getInstance().mapState.mouseClick();
        }
        setChanged();
        notifyObservers("Change current tile");
    }

    public void addDefaultTiles() {
        addTile(ClassLoader.getSystemResource("eraser.png"), "eraser.png", TileType.BACKGROUND);
        addTile(ClassLoader.getSystemResource("eraser.png"), "eraser.png", TileType.FOREGROUND);
        addTile(ClassLoader.getSystemResource("eraser.png"), "eraser.png", TileType.NPC);
        addDirectoryTilesResource("background", TileType.BACKGROUND);
        addDirectoryTilesResource("foreground", TileType.FOREGROUND);
        addDirectoryTilesResource("music", TileType.MUSIC);
    }

    public void addTile(Path file, TileType type) {
        addTile(file, type, true);
    }

    void addTile(URL file, String fileName, TileType type) {
        addTile(file, fileName, type, true);
    }

    public void addTile(Path file, TileType type, boolean askUpdate) {
        try {
            if (type == TileType.MUSIC) {
                File f = new File(file.toUri());
                addMusic(f, file.getFileName().toString());
                return;
            }
            BufferedImage img = ImageIO.read(file.toFile());
            if (img == null)
                return;
            if (type == TileType.FOREGROUND) {
                ImportedTile fore = new ImportedTile(file.getFileName().toString(), img);
                foregroundTiles.put(file.getFileName().toString(), fore);
            } else if (type == TileType.NPC) {
                Animation npc = new Animation(file.getFileName().toString(), img);
                npcTile.put(file.getFileName().toString(), npc);
            } else {
                cutAndAddBackTile(img, file.getFileName().toString());
            }
        } catch (Exception e) {
            System.err.println("Can't load this file : " + file.getFileName());
        }
        if (askUpdate)
            askUpdate();
    }

    void addTile(URL file, String fileName, TileType type, boolean askUpdate) {
        try {
            if (type == TileType.MUSIC) {
                addMusic(file, fileName);
                return;
            }
            BufferedImage img = ImageIO.read(file);
            if (img == null)
                return;
            if (type == TileType.FOREGROUND) {
                ImportedTile fore = new ImportedTile(fileName, img);
                foregroundTiles.put(fileName, fore);
            } else if (type == TileType.NPC) {
                Animation npc = new Animation(fileName, img);
                npcTile.put(fileName, npc);
            } else {
                cutAndAddBackTile(img, fileName);
            }
        } catch (Exception e) {
            System.err.println("Can't load this file : " + fileName);
            e.printStackTrace();
        }
        if (askUpdate)
            askUpdate();
    }

    public void cutAndAddBackTile(BufferedImage back, String name) {
        int height = back.getHeight();
        int width = back.getWidth();
        if (name.equals("eraser.png") || (height == 16 && width == 16)) {
            Tile tile = new Tile(name, back);
            backgroundTiles.put(name, tile);
            return;
        }
        Tile tile = new BigTile(name, back);
        backgroundTiles.put(name, tile);
    }

    public void addDirectoryTiles(String path, TileType type) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .forEach((file) -> addTile(file, type, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        askUpdate();
    }

    public void addDirectoryTilesResource(String path, TileType type) {
        try {
            CodeSource src = TilesState.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    if (name.startsWith(path)) {
                        addTile(ClassLoader.getSystemResource(name), Paths.get(name).getFileName().toString(), type, false);
                    }
                }
            } else {
                System.out.println("Can not load directory: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        askUpdate();
    }

    private void askUpdate() {
        setChanged();
        notifyObservers("Tiles Update");
    }

    public void autoAddTiles(File fileOrDir) {
        // TODO : Better algo
        if (fileOrDir.isFile()) {
            if (fileOrDir.getName().contains(".wav"))
                addMusic(fileOrDir, fileOrDir.getName());
            else
                addTile(fileOrDir.toPath(), TileType.BACKGROUND);
        }
        else if (fileOrDir.getName().contains("fore"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.FOREGROUND);
        else if (fileOrDir.getName().contains("back"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.BACKGROUND);
        else if (fileOrDir.getName().contains("npc"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.NPC);
        else if (fileOrDir.getName().contains("music"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.MUSIC);
        else {
            try (Stream<Path> paths = Files.walk(fileOrDir.toPath())) {
                paths.filter(Files::isDirectory)
                        .forEach((file) -> {
                            if (!file.toFile().getName().equals(fileOrDir.getName()))
                                autoAddTiles(file.toFile());
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMusic(File f, String name) {
        AudioInputStream inputStream = null;
        try {
            inputStream = AudioSystem.getAudioInputStream(f);
            musics.add(new Music(name, inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMusic(URL f, String name) {
        AudioInputStream inputStream = null;
        try {
            inputStream = AudioSystem.getAudioInputStream(f);
            musics.add(new Music(name, inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

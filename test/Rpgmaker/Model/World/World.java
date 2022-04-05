package Rpgmaker.Model.World;

import Rpgmaker.Model.Editor.EditorState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class World extends Observable {
    String name;
    public List<Map> maps;
    public int curIdMap = 0;
    Player player;
    public TimeCycle timeCycle;

    public World(String name) {
        this.name = name;
        maps = new ArrayList<Map>();
        player = new Player();
        timeCycle = new TimeCycle();
    }

    public World(World world) {
        this.name = world.name;
        this.maps = new ArrayList<>();
        for (Map map : world.getMaps()) {
            this.maps.add(new Map(map));
        }
        this.player = world.player;
        this.timeCycle = world.timeCycle;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public Player getPlayer() {
        return player;
    }

    public void addMap(Map map) {
        map.id = curIdMap;
        curIdMap++;
        this.maps.add(map);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean setUpLoad() {
        var backTiles = EditorState.getInstance().tilesState.backgroundTiles;
        var foreTiles = EditorState.getInstance().tilesState.foregroundTiles;
        var npcTiles = EditorState.getInstance().tilesState.npcTile;
        var musicTiles = EditorState.getInstance().tilesState.musics;
        System.out.println(backTiles);
        System.out.println(foreTiles);
        System.out.println(npcTiles);
        System.out.println(musicTiles);
        for (Map map: maps) {
            map.backgroundTile = getTileByName(backTiles, map.backgroundTile.getName());
            if (map.backgroundTile == null)
                return false;
            for (int i = 0; i < map.background.size(); i++) {
                String name = map.background.get(i).getName();
                Tile tile = getTileByName(backTiles, name);
                if (tile == null) {
                    System.out.println(name + " not found");
                    return false;
                }
                map.background.set(i, tile);
            }
            for (Point pt : map.foreground.keySet()) {
                Tile tile = foreTiles.get(map.foreground.get(pt).getTile().getName());
                if (tile == null) {
                    System.out.println(map.foreground.get(pt).getName() + " not found");
                    return false;
                }
                map.foreground.get(pt).setTile(tile);
            }
            for (NPC npc : map.npc) {
                Animation anim = npcTiles.get(npc.getAnimation().getName());
                if (anim == null) {
                    System.out.println(npc.getName() + " not found");
                    return false;
                }
                npc.setAnimation(anim);
            }
            if (map.music != null) {
                 Optional<Music> tmp = musicTiles.stream().filter(music -> music.getName().equals(map.music.name)).findFirst();
                 if (tmp.isPresent())
                     map.music = tmp.get();
                 else {
                     System.out.println(map.music + " not found");
                     return false;
                 }
            }
        }
        Animation anim = npcTiles.get(player.getAnim().getName());
        if (anim == null) {
            System.out.println(player.getAnim().getName() + " not found");
            return false;
        }
        player.setAnim(anim);
        return true;
    }

    public Tile getTileByName(java.util.Map<String, Tile> map, String name) {
        if (name.equals("PlaceHolder"))
            return Tile.getPlaceholder();
        if (name.equals("TransPlaceHolder"))
            return Tile.getTransPlaceholder();
        Tile tile = map.get(name);
        if (tile == null) {
            if (name.matches(".*_[0-9]+$")) {
                String newName = name.replaceAll("_[0-9]+$", "");
                tile = map.get(newName);
                if (tile != null && tile instanceof BigTile) {
                    int num = getIntFromName(name);
                    return ((BigTile) tile).getTile(num);
                }
            }
        }
        return tile;
    }

    public int getIntFromName(String name) {
        Pattern pattern = Pattern.compile("_([0-9]+)$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getMapById(int id) {
        for (Map m: maps) {
            if (m.id == id)
                return m;
        }
        return null;
    }
}

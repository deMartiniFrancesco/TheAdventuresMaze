package Rpgmaker.Model.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class BigTile extends Tile {
    transient private Dimension dimension;
    transient public int cur = -1;
    transient private Vector<Tile> image;
    //private boolean IsWalkable; Est-ce qu'un foreground est unwalkable par défaut ?

    public BigTile(String name, BufferedImage fullImage) {
        super(name, fullImage);

        int height = fullImage.getHeight();
        int width = fullImage.getWidth();
        if (height % 16 != 0 || width % 16 != 0)
            System.err.println("Invalid dimension");
        height /= 16;
        width /= 16;
        dimension = new Dimension(width, height);
        image = ImportedTile.cutInTiles(fullImage, dimension);
        for (int i = 0; i < image.size(); i++) {
            image.get(i).setName(name + "_" + i);
        }
    }

    public int getHeight() {
        return dimension.height;
    }

    public int getWidth() {
        return dimension.width;
    }

    public Tile getTile(int num) {
        return image.get(num);
    }
    public Tile getTile(int x, int y) {
        return image.get(x + y * dimension.width);
    }

    public Dimension getDimention() {
        return dimension;
    }

    @Override
    public void setDefaultWalkable(boolean defaultWalkable) {
        super.setDefaultWalkable(defaultWalkable);
        for (Tile tile : image) {
            tile.setDefaultWalkable(defaultWalkable);
        }
    }

    public void setCur(Integer j) {
        cur = j;
    }
}

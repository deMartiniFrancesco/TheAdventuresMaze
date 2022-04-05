package Rpgmaker.Model.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class ImportedTile extends Tile {
    transient private Dimension dimension;

    transient private Vector<Tile> image;
    //private boolean IsWalkable; Est-ce qu'un foreground est unwalkable par défaut ?

    public ImportedTile(String name, BufferedImage fullImage) {
        super(name, fullImage);

        int height = fullImage.getHeight();
        int width = fullImage.getWidth();
        if (height % 16 != 0 || width % 16 != 0)
            System.err.println("Invalid dimension");
        height /= 16;
        width /= 16;
        dimension = new Dimension(width, height);
        image = cutInTiles(fullImage, dimension);

        defaultWalkable = false;
    }

    public static Vector<Tile> cutInTiles(BufferedImage image, Dimension dim) {
        var res = new Vector<Tile>();
        for (int i  = 0; i < dim.height; i++) {
            for (int j = 0; j < dim.width; j++) {
                Tile t = new Tile(image.getSubimage(j * 16,i * 16,16, 16));
                res.add(t);
            }
        }
        return res;
    }

    public int getHeight() {
        return dimension.height;
    }

    public int getWidth() {
        return dimension.width;
    }

    public Tile getTile(int x, int y) {
        return image.get(x + y * dimension.width);
    }

    public Dimension getDimention() {
        return dimension;
    }
}

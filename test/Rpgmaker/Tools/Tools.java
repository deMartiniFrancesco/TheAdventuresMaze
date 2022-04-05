package Rpgmaker.Tools;

import java.awt.geom.Point2D;
import java.io.File;

public class Tools {
    public static File getFileFromRessources(String path) {
        return new File(getPathFromRessources(path));
    }
    public static String getPathFromRessources(String path) {
        return ClassLoader.getSystemClassLoader().getResource(path).getPath();
    }
    public static void translate2D(Point2D.Double pt, double x, double y) {
        pt.setLocation(pt.getX() + x, pt.getY() + y);
    }

    public static void main(String[] args) {
        System.out.println("Hello Tools!");
    }
}

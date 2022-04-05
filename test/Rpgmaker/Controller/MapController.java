package Rpgmaker.Controller;

import Rpgmaker.Editor.MapPanel;
import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.MapState;
import Rpgmaker.Model.Editor.Mode;
import Rpgmaker.Tools.CursorManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapController {
    MapPanel mapPanel;
    MapState mapState;

    public MapController(MapPanel mapPanel, MapState mapState) {
        this.mapPanel = mapPanel;
        this.mapState = mapState;

        mapState.addObserver(mapPanel);

        mapPanel.addMouseCompleteListener(new MouseAdapter() {
            Point mouseEnter = null;
            Point mouseOut = null;

            public void mouseEntered(MouseEvent e) {
                if (mapState.getMode() != Mode.DEFAULT) {
                    CursorManager.setCursor(Cursor.CROSSHAIR_CURSOR);
                }
                mouseMoved(e);
            }

            public void mouseExited(MouseEvent e) {
                if (mapState.getMode() != Mode.DEFAULT) {
                    CursorManager.setCursor(Cursor.DEFAULT_CURSOR);
                }
                mapState.mouseOver(null);
            }

            public void mouseMoved(MouseEvent e) {
                Point over = getPointCoords(e.getX(), e.getY());
                mapState.mouseOver(over);
            }

            public void mousePressed(MouseEvent e) {
                if (mapState.currentMap == null)
                    return;
                if (mapState.selectionIn != null) {
                    mouseEnter = null;
                    System.out.println("Deselect");
                    mapState.mousePreview(null, null);
                    return;
                }
                mouseEnter = getPointCoords(e.getX(), e.getY());
                mapState.mousePreview(mouseEnter, mouseEnter);
            }

            public void mouseReleased(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                mapState.mouseClick();
                mouseEnter = null;
                mouseOut = null;
            }

            public void mouseDragged(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                Point mouseOut = getPointCoords(e.getX(), e.getY());
                mapState.mousePreview(mouseEnter, mouseOut);
            }
        });

        mapPanel.getParent().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mapState.mousePreview(null, null);
            }
        });
    }

    public static Point getPointCoords(int x, int y) {
        int divideBy = EditorState.getInstance().showGrid ? 17 : 16;
        divideBy *= EditorState.getInstance().mapState.zoomPercent;
        return new Point(x / divideBy, y / divideBy);
    }
}

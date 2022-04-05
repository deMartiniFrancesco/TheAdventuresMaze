package Rpgmaker.Editor;

import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.ToolsEnum;
import Rpgmaker.Model.Editor.ToolsState;
import Rpgmaker.Model.World.Map;
import Rpgmaker.Model.World.Player;
import Rpgmaker.Model.World.Teleporter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {
    public ToolsEnum currentPanel;
    public ToolBoxPanel toolBoxPanel;
    public ToolTilePanel toolTilePanel;
    public ToolPlayerPanel toolPlayerPanel;
    public ToolTeleporterPanel toolTeleporterPanel;
    public ToolNPCPanel toolNPCPanel;
    public ToolForegroundPanel toolForegroundPanel;

    public ToolsPanel() {
        toolBoxPanel = new ToolBoxPanel();
        toolTilePanel = new ToolTilePanel();
        toolPlayerPanel = new ToolPlayerPanel();
        toolTeleporterPanel = new ToolTeleporterPanel();
        toolNPCPanel = new ToolNPCPanel();
        toolForegroundPanel = new ToolForegroundPanel();
        this.add(toolBoxPanel);
        this.add(toolTilePanel);
        this.add(toolPlayerPanel);
        this.add(toolTeleporterPanel);
        this.add(toolNPCPanel);
        this.add(toolForegroundPanel);

        this.setVisible(false);
    }

    public void update(Observable observable, Object o) {
        if (o instanceof ToolsEnum) {
            currentPanel = (ToolsEnum)o;
            this.setVisible(true);
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        if (!aFlag)
            return;

        toolBoxPanel.setVisible(false);
        toolTilePanel.setVisible(false);
        toolPlayerPanel.setVisible(false);
        toolTeleporterPanel.setVisible(false);
        toolNPCPanel.setVisible(false);
        toolForegroundPanel.setVisible(false);

        switch (currentPanel) {
            case TOOLBOX:
                toolBoxPanel.setVisible(true);
                if (EditorState.getInstance().world != null)
                    toolBoxPanel.showCycleSetting(EditorState.getInstance().world.timeCycle);
                break;
            case TILES:
                toolTilePanel.setVisible(true);
                break;
            case PLAYER:
                if (EditorState.getInstance().world != null)
                    toolPlayerPanel.updateInfo(EditorState.getInstance().world.getPlayer());
                toolPlayerPanel.setVisible(true);
                break;
            case TELEPORTER:
                toolTeleporterPanel.update(EditorState.getInstance().mapState.getCurrentTeleporter());
                toolTeleporterPanel.setVisible(true);
                break;
            case PNC:
                toolNPCPanel.updateInfo(EditorState.getInstance().mapState.getCurrentNPC());
                toolNPCPanel.setVisible(true);
                break;
            case FOREGROUND:
                toolForegroundPanel.updateInfo(EditorState.getInstance().mapState.getCurrentForeground());
                toolForegroundPanel.setVisible(true);
                break;
            case NONE:
                setVisible(false);
                break;
        }
    }
}

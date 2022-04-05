package Rpgmaker.Controller;

import Rpgmaker.Editor.Editor;
import Rpgmaker.Editor.TilesPanel;
import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.TileType;
import Rpgmaker.Model.Editor.TilesState;
import Rpgmaker.Model.Editor.ToolsEnum;
import Rpgmaker.Model.World.BigTile;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

public class TilesController {
    TilesPanel tilesPanel;
    TilesState tilesState;
    ToolsController toolsController;

    public TilesController(TilesPanel tilesPanel, TilesState tilesState, ToolsController toolsController) {
        this.tilesPanel = tilesPanel;
        this.tilesState = tilesState;
        this.toolsController = toolsController;
        tilesState.addObserver(toolsController.toolsPanel.toolTilePanel);
        tilesState.addObserver(tilesPanel);
        tilesState.addDefaultTiles();
        setupListener();
    }

    public void setupListener() {
        for(Map.Entry<String, JButton> entry : tilesPanel.foregroundTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.FOREGROUND);
                setupCheckboxAndBigTile();
            });
            tilesPanel.foregroundTab.searchbar.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent keyEvent) {
                    String filter = tilesPanel.foregroundTab.searchbar.getText();
                    tilesPanel.foregroundTab.filter(filter);
                }
            });
        }
        for(Map.Entry<String, JButton> entry : tilesPanel.backgroundTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.BACKGROUND);
                setupCheckboxAndBigTile();
            });
            tilesPanel.backgroundTab.searchbar.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent keyEvent) {
                    String filter = tilesPanel.backgroundTab.searchbar.getText();
                    tilesPanel.backgroundTab.filter(filter);
                }
            });
        }
        for(Map.Entry<String, JButton> entry : tilesPanel.NPCTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.NPC);
                setupCheckboxAndBigTile();
            });
            tilesPanel.NPCTab.searchbar.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent keyEvent) {
                    String filter = tilesPanel.NPCTab.searchbar.getText();
                    tilesPanel.NPCTab.filter(filter);
                }
            });
        }
    }

    public void setupCheckboxAndBigTile() {
        toolsController.toolsPanel.toolTilePanel.walkCheckbox.addItemListener((e) -> {
            tilesState.currentTile.setDefaultWalkable(e.getStateChange() == ItemEvent.SELECTED);
        });
        toolsController.toolsPanel.toolTilePanel.bigTileAll.addActionListener((e) -> {
            ((BigTile)tilesState.currentTile).setCur(-1);
        });
        toolsController.toolsPanel.toolTilePanel.setAsBackground.addActionListener(e -> {
            EditorState.getInstance().mapState.setBackgroundCurrentTile();
        });
        toolsController.toolsPanel.toolTilePanel.setPlayerAnim.addActionListener(e -> {
            EditorState.getInstance().world.getPlayer().setAnim();
            Editor.getInstance().mapPane.update(EditorState.getInstance().mapState, "Update Me");
        });
        for (int i = 0;  i < toolsController.toolsPanel.toolTilePanel.bigTileSeclector.size(); i++) {
            final Integer j = i;
            toolsController.toolsPanel.toolTilePanel.bigTileSeclector.get(i).addActionListener((e) -> {
                ((BigTile)tilesState.currentTile).setCur(j);
            });
        }
    }
}

package Rpgmaker.Controller;

import Rpgmaker.Editor.Editor;
import Rpgmaker.Editor.ToolsPanel;
import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.Editor.Mode;
import Rpgmaker.Model.Editor.ToolsEnum;
import Rpgmaker.Model.Editor.ToolsState;
import Rpgmaker.Model.World.*;
import Rpgmaker.Tools.Pair;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class ToolsController {
    ToolsPanel toolsPanel;
    ToolsState toolsState;

    public ToolsController(ToolsPanel toolsPanel, ToolsState toolsState) {
        this.toolsPanel = toolsPanel;
        this.toolsState = toolsState;
        toolsState.addObserver(toolsPanel);

        toolsPanel.toolBoxPanel.setSpawnButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.PLAYER));
        toolsPanel.toolBoxPanel.addTeleporterButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.TELEPORTER));
        toolsPanel.toolBoxPanel.showWalkable.addItemListener(e -> {
            EditorState.getInstance().mapState.setShowWalk(e.getStateChange() == ItemEvent.SELECTED);
        });
        toolsPanel.toolBoxPanel.forceWalkable.addActionListener(e -> EditorState.getInstance().mapState.forceWalkable(true));
        toolsPanel.toolBoxPanel.forceUnwalkable.addActionListener(e -> EditorState.getInstance().mapState.forceWalkable(false));
        toolsPanel.toolBoxPanel.activeTimeCycle.addActionListener(e -> EditorState.getInstance().reverseTimeCycleSetting());
        toolsPanel.toolBoxPanel.dayTime.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            EditorState.getInstance().world.timeCycle.setDayDuration((int)spinner.getModel().getValue());
        });
        toolsPanel.toolBoxPanel.nightTime.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            EditorState.getInstance().world.timeCycle.setNightDuration((int)spinner.getModel().getValue());
        });
        toolsPanel.toolBoxPanel.setMusics.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object o = e.getItem();
                if (o != null) {
                    if (o instanceof Music) {
                        Map map = EditorState.getInstance().mapState.currentMap;
                        if (map != null && e.getStateChange() == ItemEvent.SELECTED) {
                            Music music = (Music) o;
                            EditorState.getInstance().mapState.currentMap.setMusic(music);
                        }
                        System.out.println(o.getClass() + " " + o);
                    } else {
                        System.out.println(o.getClass() + " " + o);
                        EditorState.getInstance().mapState.currentMap.setMusic(null);
                    }

                }
            }
        });

        toolsPanel.toolTeleporterPanel.setDestButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.TELEPORTERDEST));
        toolsPanel.toolTeleporterPanel.deleteMe.addActionListener(e -> EditorState.getInstance().mapState.deleteTeleport());

        toolsPanel.toolNPCPanel.isMoving.addActionListener(e -> {
            JCheckBox tmp = (JCheckBox) e.getSource();
            EditorState.getInstance().mapState.getCurrentNPC().setMoving(tmp.isSelected());
        });

        toolsPanel.toolForegroundPanel.setBreakable.addItemListener(e -> {
            Foreground f = EditorState.getInstance().mapState.getCurrentForeground();
            f.isBreakable = (e.getStateChange() == ItemEvent.SELECTED);
            toolsPanel.toolForegroundPanel.UpdateBreaker(f);
        });
        toolsPanel.toolForegroundPanel.setAsHide.addItemListener(e -> {
            EditorState.getInstance().mapState.getCurrentForeground().isHided = (e.getStateChange() == ItemEvent.SELECTED);
            toolsPanel.toolForegroundPanel.UpdateHider(EditorState.getInstance().mapState.getCurrentForeground());
        });
        toolsPanel.toolForegroundPanel.setPickable.addItemListener(e -> EditorState.getInstance().mapState.getCurrentForeground().isPickable = (e.getStateChange() == ItemEvent.SELECTED));
        toolsPanel.toolForegroundPanel.hider.addItemListener(e -> {
            Object o = e.getItem();
            if (o != null) {
                if (o instanceof NPC) {
                    NPC npc = (NPC)o;
                    Point pt = EditorState.getInstance().mapState.getCurrentForeground().getPoint();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.err.println("Add " + EditorState.getInstance().mapState.getCurrentForeground() + " to hideable of " + npc);
                        if (!npc.getRevealForeground().contains(pt))
                            npc.getRevealForeground().add(pt);
                    } else {
                        System.err.println("Remove " + EditorState.getInstance().mapState.getCurrentForeground() + " to hideable of " + npc);
                        npc.getRevealForeground().remove(pt);
                    }
                }
            }
        });
        toolsPanel.toolForegroundPanel.breaker.addActionListener(e -> {
            JComboBox jcb = (JComboBox) e.getSource();
            String s = (String) jcb.getSelectedItem();
            if (s != null) {
                if (s.equals("------"))
                    s = "";
                EditorState.getInstance().mapState.getCurrentForeground().setBreaker(s);
            }
        });

        toolsPanel.toolPlayerPanel.setSpawn.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.PLAYER));
    }

    public ToolsEnum getCurrentTool() {
        return toolsState.currentTools;
    }

    public void setTool(ToolsEnum e) {
        toolsState.setCurrentTools(e);
    }

    public void setNpcMessageListener() {
        toolsPanel.toolNPCPanel.message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String newMessage = toolsPanel.toolNPCPanel.message.getText();
                EditorState.getInstance().mapState.getCurrentNPC().setMessage(newMessage);
            }
        });
        toolsPanel.toolNPCPanel.deleteMe.addActionListener(e -> EditorState.getInstance().mapState.deleteNPC());
    }
}

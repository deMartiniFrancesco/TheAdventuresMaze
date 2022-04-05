package Rpgmaker.Tools;

import Rpgmaker.Editor.Editor;
import Rpgmaker.Model.Editor.EditorState;
import Rpgmaker.Model.World.Map;

import javax.swing.*;
import java.awt.*;

public class PopUpManager {

    public static void Alert(String text) {
        JOptionPane.showMessageDialog(Editor.getInstance(), text, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void askNewMap(boolean newWorld) {

        EditorState editorState = EditorState.getInstance();

        if (newWorld && editorState.world != null && !Confirm("All unsave work will be lost"))
            return;
        JDialog frame = new JDialog(Editor.getInstance(), true);
        frame.setLayout(new GridLayout(4,2));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Choose the configuration");

        JLabel name_panel = new JLabel("Name");
        JLabel width_panel = new JLabel("Width");
        JLabel height_panel = new JLabel("Height");

        JButton validate = new JButton( "Validate");
        JButton cancel = new JButton( "Cancel");
        JTextField name = new JTextField();
        JSpinner width = new JSpinner();
        JSpinner height = new JSpinner();

        name.setSize(50, 1);
        name.setText("New map");
        name.setVisible(true);
        width.setModel(new SpinnerNumberModel(35, 1, 800, 1));
        width.setVisible(true);
        height.setModel(new SpinnerNumberModel(35, 1, 800, 1));
        height.setVisible(true);

        validate.addActionListener(e -> {
            int Mwidth = (int) width.getModel().getValue();
            int Mheight = (int) height.getModel().getValue();
            String Mname = name.getText();
            frame.dispose();
            if (newWorld)
                editorState.world = null;
            Map map = new Map(new Dimension(Mwidth, Mheight), Mname);
            if (editorState.world == null) {
                editorState.defaultWorld(map);
            } else {
                editorState.addMap(map);
            }
        });
        cancel.addActionListener(e -> frame.dispose());


        frame.add(name_panel);
        frame.add(name);
        frame.add(width_panel);
        frame.add(width);
        frame.add(height_panel);
        frame.add(height);
        frame.add(cancel);
        frame.add(validate);

        frame.validate();
        frame.pack();
        frame.setSize(250, 150);
        frame.getRootPane().setDefaultButton(validate);
        frame.setVisible(true);
    }

    public static boolean Confirm(String s) {
        return JOptionPane.showConfirmDialog(Editor.getInstance(), s) == 0;
    }

    public static String askString(String s) {
        return JOptionPane.showInputDialog(s);
    }
}

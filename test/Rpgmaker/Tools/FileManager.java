package Rpgmaker.Tools;

import Rpgmaker.Engine.Engine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    public static File getFile() {
        File file = null;
        JFileChooser fc = setUpFileChooser(JFileChooser.FILES_ONLY);
        return getElement(file, fc);
    }

    public static File getFileOrDir() {
        File file = null;
        JFileChooser fc = setUpFileChooser(JFileChooser.FILES_AND_DIRECTORIES);
        return getElement(file, fc);
    }

    private static File getElement(File file, JFileChooser fc) {
        JFrame frame = setUpFrame(fc);
        int res = fc.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        frame.setVisible(false);
        frame.dispose();
        return file;
    }

    public static void saveFile(Object o, String extension) {
        if (o == null) {
            PopUpManager.Alert("Nothing to save.");
            return;
        }
        File file = null;
        JFileChooser fc = setUpFileChooser(JFileChooser.FILES_ONLY);
        JFrame frame = setUpFrame(fc);
        int res = fc.showSaveDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if (file == null)
                return;
            if (!file.getName().endsWith(extension))
                file = new File(file.toString() + extension);
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                    .setPrettyPrinting().create();
            String world_json = gson.toJson(o);
            try {
                file.createNewFile();
                FileOutputStream writer = new FileOutputStream(file);
                writer.write(world_json.getBytes());
                if (extension.equals(".wrld"))
                    PopUpManager.Alert("World saved !");
                if (extension.equals(".save")) {
                    JOptionPane.showMessageDialog(Engine.getInstance(), "Save state saved !", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        frame.setVisible(false);
        frame.dispose();
    }

    public static File saveJar() {
        File file = null;
        JFileChooser fc = setUpFileChooser(JFileChooser.FILES_ONLY);
        JFrame frame = setUpFrame(fc);
        int res = fc.showSaveDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if (!file.getName().endsWith(".jar"))
                file = new File(file.toString() + ".jar");
        }
        frame.setVisible(false);
        frame.dispose();
        return file;
    }

    private static JFrame setUpFrame(JFileChooser fc) {
        JFrame frame = new JFrame();
        frame.setContentPane(fc);
        return frame;
    }

    private static JFileChooser setUpFileChooser(int type) {
        File current = new File(System.getProperty("user.dir"));
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(current);
        fc.setFileSelectionMode(type);
        return fc;
    }
}

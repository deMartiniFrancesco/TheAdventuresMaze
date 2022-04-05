package Rpgmaker.Tools;

import Rpgmaker.Model.World.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.jar.*;

public class JarMaker {
    public static void makeJar(World w) {
        File tofile = FileManager.saveJar();

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Engine/Main");

        String wJson = createWorldJson(w);
        InputStream wStream = new ByteArrayInputStream(wJson.getBytes(StandardCharsets.UTF_8));

        try {
            JarOutputStream target =
                    new JarOutputStream(new FileOutputStream(tofile), manifest);
            URL jar = JarMaker.class.getProtectionDomain().getCodeSource().getLocation();
            JarInputStream j = new JarInputStream(jar.openStream());
            addJar(j, target, wStream);
            addImagesJar(target, w);
            addMusicJar(target, w);
            target.close();
            PopUpManager.Alert("JAR saved !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createWorldJson(World w) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        return gson.toJson(w);
    }

    private static void addStreamToJar(InputStream in, JarOutputStream target) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int count = in.read(buffer);
            if (count == -1)
                break;
            target.write(buffer, 0, count);
        }
        target.closeEntry();
    }

    private static void addJar(JarInputStream jar, JarOutputStream target, InputStream worldStream) throws IOException {
        BufferedInputStream in = null;
        try {
            JarEntry j = jar.getNextJarEntry();
            while (j != null) {
                if (j.getName().startsWith("Editor/")) {
                    j = jar.getNextJarEntry();
                    continue;
                }
                target.putNextEntry(j);
                in = new BufferedInputStream(jar);

                addStreamToJar(in, target);

                j = jar.getNextJarEntry();
            }
            target.putNextEntry(new JarEntry("world.json"));
            addStreamToJar(new BufferedInputStream(worldStream), target);
        } finally {
            if (in != null)
                in.close();
        }
    }

    private static void addImagesJar(JarOutputStream target, World w) {
        for (Map m : w.maps) {
            for (Foreground f : m.getForegroundSet().values()) {
                addEntryImageJar(target, f.get(), "foreground/" + f.getTile().getName());
            }
            for (Tile t : m.getBackground()) {
                addEntryImageJar(target, t.get(), "background/" + t.getName());
            }
            for (NPC npc : m.getNpcs()) {
                Animation t = npc.getAnimation();
                addEntryImageJar(target, t.getFull(), "npc/" + t.getName());
            }
            addEntryImageJar(target, m.getBackgroundTile().get(), "background/" + m.getBackgroundTile().getName());
            addEntryImageJar(target, w.getPlayer().getAnim().getFull(), "npc/" + w.getPlayer().getAnim().getName());
        }
    }

    private static void addMusicJar(JarOutputStream target, World w) {
        for (Map m : w.maps) {
            Music music = m.getMusic();
            if (music != null)
                addEntryMusicJar(target, music, "music/" + music.getName());
        }
    }

    public static void addEntryImageJar(JarOutputStream target, BufferedImage obj, String name) {
        try {
            target.putNextEntry(new JarEntry(name));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(obj, "png", out);

            addStreamToJar(new BufferedInputStream(new ByteArrayInputStream(out.toByteArray())), target);
        } catch (Exception e) {
        }
    }

    public static void addEntryMusicJar(JarOutputStream target, Music music, String name) {
        try {
            target.putNextEntry(new JarEntry(name));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            music.getAudioInputStream().reset();
            AudioSystem.write(music.getAudioInputStream(), AudioFileFormat.Type.WAVE, out);
            addStreamToJar(new BufferedInputStream(new ByteArrayInputStream(out.toByteArray())), target);
        } catch (Exception e) {
        }
    }
}

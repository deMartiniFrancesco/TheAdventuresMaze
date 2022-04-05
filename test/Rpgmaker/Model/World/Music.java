package Rpgmaker.Model.World;

import javax.sound.sampled.*;

public class Music {
    String name;
    transient AudioInputStream audioStream;
    transient static Clip clip = null;
    public transient static String musicPlayed = "";

    public Music(String name, AudioInputStream inputStream) throws Exception {
        this.name = name;
        this.audioStream = inputStream;
        audioStream.mark(Integer.MAX_VALUE);
        if (clip == null)
            clip = AudioSystem.getClip();
    }

    public static void pause() {
        if (clip != null)
            clip.stop();
    }

    public static void restart() {
        if (clip != null)
            clip.start();
    }

    public static void destroy() {
        if (clip != null) {
            clip.close();
        }
        clip = null;
        musicPlayed = "";
    }

    public static void stop() {
        try {
            if (clip != null)
                clip.close();
            musicPlayed = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public void play() throws Exception {
        if (musicPlayed.equals(name))
            return;
        clip = null;
        audioStream.reset();
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.setFramePosition(0);
        clip.setMicrosecondPosition(0);
        clip.start();
        musicPlayed = name;
    }

    public AudioInputStream getAudioInputStream() {
        return audioStream;
    }
}

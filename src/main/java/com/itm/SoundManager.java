package com.itm;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final Map<String, Clip> cache = new HashMap<>();
    private static Clip clipLoop;

    public static void precargar(String... archivos) {
        for (String archivo : archivos) {
            try {
                URL url = SoundManager.class.getResource("/sounds/" + archivo);
                if (url == null) continue;
                AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                cache.put(archivo, clip);
            } catch (Exception e) {}
        }
    }

    public static void reproducir(String archivo) {
        try {
            Clip clip = cache.get(archivo);
            if (clip == null) return;
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {}
    }

    public static void reproducirLoop(String archivo) {
        detenerLoop();
        try {
            Clip clip = cache.get(archivo);
            if (clip == null) return;
            clip.setFramePosition(0);
            clipLoop = clip;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {}
    }

    public static void detenerLoop() {
        if (clipLoop != null && clipLoop.isRunning()) {
            clipLoop.stop();
        }
        clipLoop = null;
    }
}

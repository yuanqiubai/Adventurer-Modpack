/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  blm
 *  bln
 *  blq
 */
package noppes.client.controllers;

import java.util.TreeSet;
import noppes.client.AssetsBrowser;

public class MusicController {
    public static MusicController Instance;
    public TreeSet<String> sounds = new TreeSet();
    public TreeSet<String> music = new TreeSet();
    public String playing = "";
    private final blq musicPool;
    private int soundID = 0;

    public MusicController() {
        Instance = this;
        this.musicPool = new blq(atv.w().K(), "music", false);
        this.loadSounds();
    }

    private void loadSounds() {
        System.out.println("loading sounds");
        this.checkFolder(true, atv.w().v.d, "/customnpcs/sound/", "customnpcs:");
        this.checkFolder(false, this.musicPool, "/customnpcs/music/", "customnpcs:");
    }

    private void checkFolder(boolean isSounds, blq pool, String folder, String name) {
        AssetsBrowser browser = new AssetsBrowser(folder, new String[]{"wav", "ogg"});
        for (String map : browser.files) {
            String dir = name + map;
            pool.a(dir);
            this.addSound(isSounds, dir);
        }
        for (String map : browser.folders) {
            this.checkFolder(isSounds, pool, folder + map + "/", name + map + "/");
        }
    }

    private void addSound(boolean isSounds, String sound) {
        sound = sound.substring(0, sound.indexOf("."));
        sound = sound.replaceAll("/", ".");
        if (isSounds) {
            while (Character.isDigit(sound.charAt(sound.length() - 1))) {
                sound = sound.substring(0, sound.length() - 1);
            }
            this.sounds.add(sound);
        } else {
            this.music.add(sound);
        }
    }

    public void stopMusic() {
        bln manager = atv.w().v;
        if (manager != null) {
            if (manager.b.playing("streaming")) {
                manager.b.stop("streaming");
            }
            if (manager.b.playing("BgMusic")) {
                manager.b.stop("BgMusic");
            }
            this.playing = "";
        }
    }

    public void playStreaming(String music, float x, float y, float z) {
        blm soundpoolentry;
        atv mc = atv.w();
        if (mc.u.b != 0.0f && music != null && (soundpoolentry = this.musicPool.b(music)) != null) {
            this.playing = music;
            bln manager = mc.v;
            manager.d();
            manager.b.newStreamingSource(true, "streaming", soundpoolentry.b(), soundpoolentry.a(), false, x, y, z, 2, 64.0f);
            manager.b.setVolume("streaming", 0.5f * mc.u.b);
            manager.b.play("streaming");
        }
    }

    public void playMusic(String music) {
        blm soundpoolentry;
        atv mc = atv.w();
        if (mc.u.b != 0.0f && music != null && (soundpoolentry = this.musicPool.b(music)) != null) {
            this.playing = music;
            bln manager = mc.v;
            manager.d();
            manager.b.backgroundMusic("streaming", soundpoolentry.b(), soundpoolentry.a(), false);
            manager.b.setVolume("streaming", 0.5f * mc.u.b);
            manager.b.play("streaming");
        }
    }

    public boolean isStreaming() {
        bln manager = atv.w().v;
        return manager.b != null ? manager.b.playing("streaming") : false;
    }

    public boolean isPlaying(String sound) {
        return this.isStreaming() && sound.equals(this.playing);
    }

    public void playSound(String music, float x, float y, float z) {
        blm soundpoolentry;
        atv mc = atv.w();
        if (mc.u.b != 0.0f && music != null && (soundpoolentry = this.musicPool.b(music)) != null) {
            String sound = "csound_" + this.soundID++;
            bln manager = mc.v;
            manager.b.newSource(true, sound, soundpoolentry.b(), soundpoolentry.a(), false, x, y, z, 2, 64.0f);
            manager.b.setVolume(sound, 0.5f * mc.u.b);
            manager.b.play(sound);
        }
    }
}


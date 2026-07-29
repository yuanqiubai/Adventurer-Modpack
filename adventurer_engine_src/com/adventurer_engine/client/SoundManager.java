/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bln
 *  net.minecraftforge.client.event.sound.SoundLoadEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeSubscribe
 */
package com.adventurer_engine.client;

import com.adventurer_engine.util.DebugGadgets;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundManager {
    @SoundName
    public static final String RISING_CUT_BOMB = "adventurer_engine:risingCut_bomb";
    @SoundName
    public static final String RISING_CUT_FALL = "adventurer_engine:risingCut_fall";
    @SoundName
    public static final String ENDING_BLOW = "adventurer_engine:dizzy_bomb";
    @SoundName
    public static final String DEFEND = "adventurer_engine:defend";
    @SoundName
    public static final String DEFEND_STRONG = "adventurer_engine:defend_strong";
    @SoundName
    public static final String DEFEND_JUST = "adventurer_engine:defend_just";
    @SoundName
    public static final String SLIDE_STEP_SOUND = "adventurer_engine:slide_step";
    @SoundName
    public static final String SWITCH_AMMO = "adventurer_engine:switch_ammo";
    private static Set<String> sounds = new HashSet<String>();

    public SoundManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @ForgeSubscribe
    public void onLoadSound(SoundLoadEvent event) {
        Field[] fields;
        bln manager = event.manager;
        for (String s : sounds) {
            manager.a(String.format("%s.ogg", s));
        }
        for (Field field : fields = this.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) || !field.getType().equals(String.class) || !field.isAnnotationPresent(SoundName.class.asSubclass(Annotation.class))) continue;
            try {
                String value = (String)field.get(null);
                manager.a(String.format("%s.ogg", value));
            }
            catch (IllegalAccessException e) {
                DebugGadgets.debugInfo(e.getMessage());
            }
        }
    }

    public static void addSound(String soundName) {
        sounds.add("adventurer_engine:" + soundName);
    }

    static @interface SoundName {
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ats
 *  atv
 *  cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler
 *  cpw.mods.fml.common.TickType
 *  uf
 */
package noppes.client.events;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import noppes.client.gui.player.GuiQuestLog;
import noppes.util.NoppesUtil;

public class ClientKeyHandler
extends KeyBindingRegistry.KeyHandler {
    public ClientKeyHandler(ats[] keyBindings, boolean[] repeatings) {
        super(keyBindings, repeatings);
    }

    public String getLabel() {
        return null;
    }

    public void keyDown(EnumSet<TickType> types, ats kb, boolean tickEnd, boolean isRepeat) {
    }

    public void keyUp(EnumSet<TickType> types, ats kb, boolean tickEnd) {
        if (kb.c.equals("Quest Log") && tickEnd) {
            atv mc = atv.w();
            if (mc.n == null) {
                NoppesUtil.openGUI((uf)mc.h, new GuiQuestLog((uf)mc.h));
            } else if (mc.n instanceof GuiQuestLog) {
                mc.g();
            }
        }
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }
}


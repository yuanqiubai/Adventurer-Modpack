/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  atv
 *  aut
 *  awe
 *  axv
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  uf
 *  vv
 */
package noppes.client.events;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.EnumSet;
import java.util.Iterator;
import noppes.CustomNpcs;
import noppes.client.controllers.MusicController;
import noppes.client.gui.player.GuiFaction;
import noppes.client.gui.player.GuiQuestLog;
import noppes.client.gui.util.GuiMenuTopButton;
import noppes.client.gui.util.IButtonListener;
import noppes.common.config.CustomNPCConfig;
import noppes.network.player.QuestPackets;

public class ClientTickHandler
implements ITickHandler {
    private abw prevWorld;
    private boolean otherContainer = false;

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
    }

    public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
        if (type.contains(TickType.CLIENT)) {
            atv mc = atv.w();
            if (mc.h != null && mc.h.bp instanceof vv) {
                if (this.otherContainer) {
                    PacketDispatcher.sendPacketToServer((ey)new QuestPackets.CheckQuestCompletionPacket().makePacket("CNPCs Player"));
                    this.otherContainer = false;
                }
            } else {
                this.otherContainer = true;
            }
            awe guiscreen = mc.n;
            if (CustomNPCConfig.InventoryGuiEnabled && guiscreen instanceof axv && !this.guiHasButtons(guiscreen)) {
                axv guiInventory = (axv)guiscreen;
                IButtonListener listener = new IButtonListener(){

                    @Override
                    public void actionPerformed(aut button) {
                        atv mc = atv.w();
                        if (button.g == 2) {
                            mc.a((awe)new GuiQuestLog((uf)mc.h));
                        }
                        if (button.g == 3) {
                            mc.a((awe)new GuiFaction());
                        }
                    }
                };
                GuiMenuTopButton inv = new GuiMenuTopButton(1, guiInventory.p + 3, guiInventory.q - 17, "menu.inventory");
                GuiMenuTopButton quests = new GuiMenuTopButton(2, inv, "quest.quests", listener);
                GuiMenuTopButton factions = new GuiMenuTopButton(3, quests, "menu.factions", listener);
                inv.active = true;
                guiInventory.i.add(inv);
                guiInventory.i.add(quests);
                guiInventory.i.add(factions);
            }
            ++CustomNpcs.ticks;
            if (this.prevWorld != mc.f) {
                MusicController.Instance.playing = "";
                this.prevWorld = mc.f;
            }
        }
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.WORLD);
    }

    public boolean guiHasButtons(awe guiscreen) {
        Object ob;
        Iterator i$ = guiscreen.i.iterator();
        do {
            if (i$.hasNext()) continue;
            return false;
        } while (!((ob = i$.next()) instanceof GuiMenuTopButton));
        return true;
    }

    public String getLabel() {
        return "CNPCs ClientTickHandler";
    }
}


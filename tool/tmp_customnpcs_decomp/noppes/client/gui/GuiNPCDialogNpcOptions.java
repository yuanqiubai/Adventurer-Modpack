/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import noppes.client.gui.GuiNPCDialogSelection;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IGuiData;
import noppes.common.controllers.DialogOption;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditDialogPackets;
import noppes.util.NoppesUtil;

public class GuiNPCDialogNpcOptions
extends GuiNPCInterface2
implements GuiSelectionListener,
IGuiData {
    private final awe parent;
    private final HashMap<Integer, DialogOption> data = new HashMap();
    private int selectedSlot;
    private static final ey DIALOG_NPC_GET = new EditDialogPackets.DialogNpcGetPacket().makePacket("CNPCs Server");

    public GuiNPCDialogNpcOptions(EntityNPCInterface npc, awe parent) {
        super(npc);
        this.parent = parent;
        this.drawDefaultBackground = true;
        PacketDispatcher.sendPacketToServer((ey)DIALOG_NPC_GET);
    }

    @Override
    public void A_() {
        super.A_();
        for (int i = 0; i < 12; ++i) {
            int offset = i >= 6 ? 200 : 0;
            this.addButton(new GuiNpcButton(i + 20, this.guiLeft + 20 + offset, this.guiTop + 13 + i % 6 * 22, 20, 20, "X"));
            this.addLabel(new GuiNpcLabel(i, String.valueOf(i), this.guiLeft + 6 + offset, this.guiTop + 18 + i % 6 * 22, 0));
            String title = "dialog.selectoption";
            if (this.data.containsKey(i)) {
                title = this.data.get((Object)Integer.valueOf((int)i)).title;
            }
            this.addButton(new GuiNpcButton(i, this.guiLeft + 44 + offset, this.guiTop + 13 + i % 6 * 22, 140, 20, title));
        }
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        int slot;
        if (guibutton.g == 1) {
            NoppesUtil.openGUI(this.player, this.parent);
        }
        if (guibutton.g >= 0 && guibutton.g < 20) {
            this.close();
            this.selectedSlot = guibutton.g;
            slot = -1;
            if (this.data.containsKey(guibutton.g)) {
                slot = this.data.get((Object)Integer.valueOf((int)guibutton.g)).dialogId;
            }
            NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, slot));
        }
        if (guibutton.g >= 20 && guibutton.g < 40) {
            slot = guibutton.g - 20;
            this.data.remove(slot);
            PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogNpcRemovePacket(slot).makePacket("CNPCs Server"));
            this.A_();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void selected(int id) {
        PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogNpcSetPacket(this.selectedSlot, id).makePacket("CNPCs Server"));
    }

    @Override
    public void setGuiData(by compound) {
        int pos = compound.e("Position");
        DialogOption dialog = new DialogOption();
        dialog.readNBT(compound);
        this.data.put(pos, dialog);
        this.A_();
    }
}


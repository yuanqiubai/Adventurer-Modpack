/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobHealer;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcHealer
extends GuiNPCInterface2 {
    private final JobHealer job;

    public GuiNpcHealer(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobHealer)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(1, "Healing Speed:", this.guiLeft + 60, this.guiTop + 110, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 130, this.guiTop + 105, 40, 20, String.valueOf(this.job.speed)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(1, 10, 8);
        this.addLabel(new GuiNpcLabel(2, "Range:", this.guiLeft + 60, this.guiTop + 133, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 130, this.guiTop + 128, 40, 20, String.valueOf(this.job.range)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(2, 20, 5);
    }

    @Override
    public void elementClicked() {
    }

    @Override
    public void save() {
        this.job.speed = this.getTextField(1).getInteger();
        this.job.range = this.getTextField(2).getInteger();
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}


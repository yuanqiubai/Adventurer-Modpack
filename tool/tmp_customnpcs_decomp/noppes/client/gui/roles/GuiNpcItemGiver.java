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
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.containers.ContainerNpcItemGiver;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobItemGiver;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcItemGiver
extends GuiContainerNPCInterface2 {
    private final JobItemGiver role;

    public GuiNpcItemGiver(EntityNPCInterface npc, ContainerNpcItemGiver container) {
        super(npc, container);
        this.role = (JobItemGiver)npc.jobInterface;
        this.d = 182;
        this.setBackground("npcitemgiver.png");
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.p + 6, this.q + 6, 140, 20, new String[]{"Random Item", "All Items", "Give Not Owned Items", "Give When Doesnt Own Any", "Chained"}, this.role.givingMethod));
        this.addButton(new GuiNpcButton(1, this.p + 6, this.q + 29, 140, 20, new String[]{"Timer", "Give Only Once", "Daily"}, this.role.cooldownType));
        this.addTextField(new GuiNpcTextField(0, (awe)this, this.o, this.p + 55, this.q + 54, 90, 20, String.valueOf(this.role.cooldown)));
        this.getTextField((int)0).numbersOnly = true;
        this.addLabel(new GuiNpcLabel(0, "Cooldown:", this.p + 6, this.q + 59, 0x404040));
        this.addLabel(new GuiNpcLabel(1, "Items to give", this.p + 46, this.q + 79, 0x404040));
        this.getTextField((int)0).numbersOnly = true;
        int i = 0;
        for (String line : this.role.lines) {
            this.addTextField(new GuiNpcTextField(i + 1, (awe)this, this.o, this.p + 150, this.q + 6 + i * 24, 236, 20, line));
            ++i;
        }
        while (i < 3) {
            this.addTextField(new GuiNpcTextField(i + 1, (awe)this, this.o, this.p + 150, this.q + 6 + i * 24, 236, 20, ""));
            ++i;
        }
        this.getTextField((int)0).enabled = this.role.isOnTimer();
        this.getLabel((int)0).enabled = this.role.isOnTimer();
    }

    @Override
    public void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.role.givingMethod = button.getValue();
        }
        if (guibutton.g == 1) {
            this.role.cooldownType = button.getValue();
            this.getTextField((int)0).enabled = this.role.isOnTimer();
            this.getLabel((int)0).enabled = this.role.isOnTimer();
        }
    }

    @Override
    public void save() {
        int cc;
        ArrayList<String> lines = new ArrayList<String>();
        for (cc = 1; cc < 4; ++cc) {
            GuiNpcTextField tf = this.getTextField(cc);
            if (tf.isEmpty()) continue;
            lines.add(tf.b());
        }
        this.role.lines = lines;
        cc = 10;
        if (!this.getTextField(0).isEmpty() && this.getTextField(0).isInteger()) {
            cc = this.getTextField(0).getInteger();
        }
        this.role.cooldown = cc;
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}


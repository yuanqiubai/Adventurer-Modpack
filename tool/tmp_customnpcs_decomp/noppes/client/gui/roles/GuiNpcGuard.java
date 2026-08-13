/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobGuard;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcGuard
extends GuiNPCInterface2 {
    private final JobGuard role;

    public GuiNpcGuard(EntityNPCInterface npc) {
        super(npc);
        this.role = (JobGuard)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.guiLeft + 85, this.guiTop + 20, new String[]{"Dont Attack Animals", "Attack Animals"}, this.role.attacksAnimals ? 1 : 0));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 85, this.guiTop + 43, new String[]{"Dont Attack Monsters", "Attack Monsters"}, this.role.attackHostileMobs ? 1 : 0));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 85, this.guiTop + 66, new String[]{"Dont Attack Creepers", "Attack Creepers"}, this.role.attackCreepers ? 1 : 0));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 89, new String[]{"Dont Attack Other Mobs", "Attack Other Mobs"}, this.role.attackAll ? 1 : 0));
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            boolean bl = this.role.attacksAnimals = button.getValue() == 1;
        }
        if (guibutton.g == 1) {
            boolean bl = this.role.attackHostileMobs = button.getValue() == 1;
        }
        if (guibutton.g == 2) {
            boolean bl = this.role.attackAll = button.getValue() == 1;
        }
        if (guibutton.g == 4) {
            this.role.attackCreepers = button.getValue() == 1;
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}


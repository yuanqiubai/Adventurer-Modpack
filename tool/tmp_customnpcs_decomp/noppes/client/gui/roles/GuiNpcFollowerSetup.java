/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  ye
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.containers.ContainerNPCFollowerSetup;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleFollower;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcFollowerSetup
extends GuiContainerNPCInterface2 {
    private static final bjo craftingTableGuiTextures = new bjo("textures/gui/followersetup.png");
    private final RoleFollower role;

    public GuiNpcFollowerSetup(EntityNPCInterface npc, ContainerNPCFollowerSetup container) {
        super(npc, container);
        this.role = (RoleFollower)npc.roleInterface;
        this.d = 180;
        this.setBackground("followersetup.png");
    }

    @Override
    public void A_() {
        int i;
        super.A_();
        for (i = 0; i < 3; ++i) {
            int i$ = this.p + 66;
            int day = this.q + 37;
            GuiNpcTextField tf = new GuiNpcTextField(i, (awe)this, this.o, i$, day += i * 25, 24, 20, "1");
            tf.numbersOnly = true;
            tf.setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            this.addTextField(tf);
        }
        i = 0;
        for (int day : this.role.rates.values()) {
            this.getTextField(i).a(String.valueOf(day));
            ++i;
        }
        this.addTextField(new GuiNpcTextField(3, (awe)this, this.o, this.p + 100, this.q + 6, 286, 20, this.role.dialogHire));
        this.addTextField(new GuiNpcTextField(4, (awe)this, this.o, this.p + 100, this.q + 30, 286, 20, this.role.dialogFarewell));
    }

    @Override
    protected void a(aut guibutton) {
    }

    public void b() {
    }

    protected void b(int par1, int par2) {
    }

    @Override
    public void save() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.role.inventory.j_(); ++i) {
            ye item = this.role.inventory.a(i);
            if (item == null) continue;
            int days = 1;
            if (!this.getTextField(i).isEmpty() && this.getTextField(i).isInteger()) {
                days = this.getTextField(i).getInteger();
            }
            if (days <= 0) {
                days = 1;
            }
            map.put(i, days);
        }
        this.role.rates = map;
        this.role.dialogHire = this.getTextField(3).b();
        this.role.dialogFarewell = this.getTextField(4).b();
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}


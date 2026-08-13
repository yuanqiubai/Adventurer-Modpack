/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  aut
 *  bjo
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.containers.ContainerNPCFollowerHire;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleFollower;
import noppes.network.player.FollowerPackets;
import org.lwjgl.opengl.GL11;

public class GuiNpcFollowerHire
extends GuiContainerNPCInterface {
    private final bjo resource = new bjo("customnpcs", "textures/gui/followerhire.png");
    private final EntityNPCInterface npc;
    private final ContainerNPCFollowerHire container;
    private final RoleFollower role;

    public GuiNpcFollowerHire(EntityNPCInterface npc, ContainerNPCFollowerHire container) {
        super(npc, container);
        this.container = container;
        this.npc = npc;
        this.role = (RoleFollower)npc.roleInterface;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(5, this.p + 26, this.q + 60, 50, 20, bu.a((String)"follower.hire")));
    }

    public void a(aut guibutton) {
        super.a(guibutton);
        if (guibutton.g == 5) {
            PacketDispatcher.sendPacketToServer((ey)new FollowerPackets.FollowerHirePacket().makePacket("CNPCs Player"));
            this.close();
        }
    }

    @Override
    protected void b(int par1, int par2) {
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = (this.g - this.c) / 2;
        int i1 = (this.h - this.d) / 2;
        this.b(l, i1, 0, 0, this.c, this.d);
        int index = 0;
        for (Integer integer : this.role.inventory.items.keySet()) {
            int id = integer;
            ye itemstack = this.role.inventory.items.get(id);
            if (itemstack == null) continue;
            int days = 1;
            if (this.role.rates.containsKey(id)) {
                days = this.role.rates.get(id);
            }
            int yOffset = index * 26;
            int x = this.p + 78;
            int y = this.q + yOffset + 10;
            GL11.glEnable((int)32826);
            att.c();
            b.a(this.o, this.f.N, itemstack, x + 11, y);
            b.c(this.o, this.f.N, itemstack, x + 11, y);
            att.a();
            GL11.glDisable((int)32826);
            String daysS = days + " " + (days == 1 ? bu.a((String)"follower.day") : bu.a((String)"follower.days"));
            this.o.b(" = " + daysS, x + 27, y + 4, 0x404040);
            ++index;
        }
    }

    @Override
    public void save() {
    }
}


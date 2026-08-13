/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  aut
 *  bgl
 *  bjo
 *  bma
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  nn
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcSkinPreviewInterface;
import noppes.common.containers.ContainerNPCFollower;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleFollower;
import noppes.network.player.FollowerPackets;
import org.lwjgl.opengl.GL11;

public class GuiNpcFollower
extends GuiContainerNPCInterface
implements GuiNpcSkinPreviewInterface {
    private final bjo resource = new bjo("customnpcs", "textures/gui/follower.png");
    private final EntityNPCInterface npc;
    private final RoleFollower role;
    private float xSize_lo;
    private float ySize_lo;

    public GuiNpcFollower(EntityNPCInterface npc, ContainerNPCFollower container) {
        super(npc, container);
        this.npc = npc;
        this.role = (RoleFollower)npc.roleInterface;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.i.clear();
        this.addButton(new GuiNpcButton(4, this.p + 100, this.q + 110, 50, 20, new String[]{bu.a((String)"follower.waiting"), bu.a((String)"follower.following")}, this.role.isFollowing ? 1 : 0));
        this.addButton(new GuiNpcButton(5, this.p + 8, this.q + 30, 50, 20, bu.a((String)"follower.hire")));
    }

    public void a(aut guibutton) {
        super.a(guibutton);
        if (guibutton.g == 4) {
            PacketDispatcher.sendPacketToServer((ey)new FollowerPackets.FollowerStatePacket().makePacket("CNPCs Player"));
            this.close();
        }
        if (guibutton.g == 5) {
            PacketDispatcher.sendPacketToServer((ey)new FollowerPackets.FollowerExtendPacket().makePacket("CNPCs Player"));
            this.close();
        }
    }

    @Override
    protected void b(int par1, int par2) {
        this.o.b(bu.a((String)"follower.health") + ": " + this.npc.aN() + "/" + this.npc.aT(), 62, 70, 0x404040);
        if (this.role.getDaysLeft() <= 1) {
            this.o.b(bu.a((String)"follower.daysleft") + ": " + bu.a((String)"follower.lastday"), 62, 94, 0x404040);
        } else {
            this.o.b(bu.a((String)"follower.daysleft") + ": " + (this.role.getDaysLeft() - 1), 62, 94, 0x404040);
        }
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = this.p;
        int i1 = this.q;
        this.b(l, i1, 0, 0, this.c, this.d);
        int index = 0;
        for (int f2 : this.role.inventory.items.keySet()) {
            ye f3 = this.role.inventory.items.get(f2);
            if (f3 == null) continue;
            int f4 = 1;
            if (this.role.rates.containsKey(f2)) {
                f4 = this.role.rates.get(f2);
            }
            int f5 = index * 20;
            int f6 = this.p + 68;
            int y = this.q + f5 + 4;
            GL11.glEnable((int)32826);
            att.c();
            b.a(this.o, this.f.N, f3, f6 + 11, y);
            b.c(this.o, this.f.N, f3, f6 + 11, y);
            att.a();
            GL11.glDisable((int)32826);
            String daysS = f4 + " " + (f4 == 1 ? bu.a((String)"follower.day") : bu.a((String)"follower.days"));
            this.o.b(" = " + daysS, f6 + 27, y + 4, 0x404040);
            ++index;
        }
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), (float)50.0f);
        float var15 = 30.0f;
        GL11.glScalef((float)(-var15), (float)var15, (float)var15);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float var16 = this.npc.aN;
        float var17 = this.npc.A;
        float var18 = this.npc.B;
        float var19 = (float)(l + 33) - this.xSize_lo;
        float var20 = (float)(i1 + 131 - 50) - this.ySize_lo;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        att.b();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(var20 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.npc.aN = (float)Math.atan(var19 / 40.0f) * 20.0f;
        this.npc.A = (float)Math.atan(var19 / 40.0f) * 40.0f;
        this.npc.B = -((float)Math.atan(var20 / 40.0f)) * 20.0f;
        this.npc.aP = this.npc.A;
        GL11.glTranslatef((float)0.0f, (float)this.npc.N, (float)0.0f);
        bgl.a.j = 180.0f;
        bgl.a.a((nn)this.npc, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.npc.aN = var16;
        this.npc.A = var17;
        this.npc.B = var18;
        GL11.glPopMatrix();
        att.a();
        GL11.glDisable((int)32826);
        bma.a((int)bma.b);
        GL11.glDisable((int)3553);
        bma.a((int)bma.a);
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
        this.xSize_lo = i;
        this.ySize_lo = j;
    }

    @Override
    public void save() {
    }
}


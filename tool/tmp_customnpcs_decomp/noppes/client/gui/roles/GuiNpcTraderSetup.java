/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.common.containers.ContainerNPCTraderSetup;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditMainMenuPackets;
import org.lwjgl.opengl.GL11;

public class GuiNpcTraderSetup
extends GuiContainerNPCInterface2 {
    private static final bjo craftingTableGuiTextures = new bjo("customnpcs", "textures/gui/npctradersetup2.png");

    public GuiNpcTraderSetup(EntityNPCInterface npc, ContainerNPCTraderSetup container) {
        super(npc, container);
        this.d = 180;
    }

    @Override
    public void A_() {
        super.A_();
        this.i.clear();
        this.setBackground("npctradersetup.png");
    }

    protected void b(int par1, int par2) {
        super.b(par1, par2);
        for (int ii = 0; ii < 18; ++ii) {
            int x = 212;
            int var6 = x + ii % 3 * 59;
            int y = 16;
            int var7 = y + ii / 3 * 22;
            this.o.b("=", var6, var7 + 4, 0x404040);
        }
    }

    @Override
    public void c() {
        super.c();
    }

    @Override
    protected void a(int i, int j, int k) {
        super.a(i, j, k);
    }

    @Override
    public void a(char c, int i) {
    }

    @Override
    protected void a(float f, int i, int j) {
        super.a(f, i, j);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(craftingTableGuiTextures);
        this.b(this.p + 190, this.q + 20, 0, 0, 167, 134);
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}


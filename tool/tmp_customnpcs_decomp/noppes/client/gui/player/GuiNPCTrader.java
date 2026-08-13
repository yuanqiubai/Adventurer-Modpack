/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  bjo
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.client.gui.player;

import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.common.containers.ContainerNPCTrader;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleTrader;
import org.lwjgl.opengl.GL11;

public class GuiNPCTrader
extends GuiContainerNPCInterface {
    private final bjo resource = new bjo("customnpcs", "textures/gui/npctrader.png");
    private final RoleTrader role;

    public GuiNPCTrader(EntityNPCInterface npc, ContainerNPCTrader container) {
        super(npc, container);
        this.role = (RoleTrader)npc.roleInterface;
        this.closeOnEsc = true;
        this.d = 232;
    }

    @Override
    public void A_() {
        super.A_();
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = (this.g - this.c) / 2;
        int i1 = (this.h - this.d) / 2;
        this.b(l, i1, 0, 0, this.c, this.d);
        for (Integer slot : this.role.inventoryCurrency.items.keySet()) {
            ye item = this.role.inventoryCurrency.items.get((int)slot);
            if (item == null || this.role.inventorySold.items.get((int)slot) == null) continue;
            int x = this.p + slot % 3 * 45 + 40;
            int y = this.q + slot / 3 * 22 + 8;
            GL11.glEnable((int)32826);
            att.c();
            b.a(this.o, this.f.N, item, x, y);
            b.c(this.o, this.f.N, item, x, y);
            att.a();
            GL11.glDisable((int)32826);
            this.o.b("=", x + 16, y + 4, 0x404040);
        }
    }

    @Override
    public void a(int par1, int par2, float par3) {
        super.a(par1, par2, par3);
        for (Integer slot : this.role.inventoryCurrency.items.keySet()) {
            int y;
            int x;
            ye item = this.role.inventoryCurrency.items.get((int)slot);
            if (item == null || this.role.inventorySold.items.get((int)slot) == null || !this.c((x = this.p + slot % 3 * 45 + 40) - this.p, (y = this.q + slot / 3 * 22 + 8) - this.q, 16, 16, par1, par2)) continue;
            this.a(item, par1, par2);
        }
    }

    @Override
    public void save() {
    }
}


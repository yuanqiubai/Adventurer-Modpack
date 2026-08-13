/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  aut
 *  bjo
 *  bu
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.IGuiData;
import noppes.common.containers.ContainerNPCBankInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.player.BankPackets;
import org.lwjgl.opengl.GL11;

public class GuiNPCBankChest
extends GuiContainerNPCInterface
implements IGuiData {
    private final bjo resource = new bjo("customnpcs", "textures/gui/bankchest.png");
    private final ContainerNPCBankInterface container;
    private int availableSlots = 0;
    private int maxSlots = 1;
    private int unlockedSlots = 1;
    private ye currency;

    public GuiNPCBankChest(EntityNPCInterface npc, ContainerNPCBankInterface container) {
        super(npc, container);
        this.container = container;
        this.title = "";
        this.j = false;
        this.d = 235;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.availableSlots = 0;
        if (this.maxSlots > 1) {
            for (int i = 0; i < this.maxSlots; ++i) {
                GuiNpcButton button = new GuiNpcButton(i, this.p - 50, this.q + 10 + i * 24, 50, 20, "Tab " + (i + 1));
                if (i > this.unlockedSlots) {
                    button.h = false;
                }
                this.addButton(button);
                ++this.availableSlots;
            }
            if (this.availableSlots == 1) {
                this.i.clear();
            }
        }
        if (!this.container.isAvailable()) {
            this.addButton(new GuiNpcButton(8, this.p + 48, this.q + 48, 80, 20, bu.a((String)"bank.unlock")));
        } else if (this.container.canBeUpgraded()) {
            this.addButton(new GuiNpcButton(9, this.p + 48, this.q + 48, 80, 20, bu.a((String)"bank.upgrade")));
        }
        if (this.maxSlots > 1) {
            this.getButton((int)this.container.slot).shown = false;
            this.getButton((int)this.container.slot).h = false;
        }
    }

    public void a(aut guibutton) {
        super.a(guibutton);
        if (guibutton.g < 6) {
            this.close();
            PacketDispatcher.sendPacketToServer((ey)new BankPackets.BankSlotOpenPacket(guibutton.g, this.container.bankid).makePacket("CNPCs Player"));
        }
        if (guibutton.g == 8) {
            PacketDispatcher.sendPacketToServer((ey)new BankPackets.BankUnlockPacket().makePacket("CNPCs Player"));
        }
        if (guibutton.g == 9) {
            PacketDispatcher.sendPacketToServer((ey)new BankPackets.BankUpgradePacket().makePacket("CNPCs Player"));
        }
    }

    @Override
    protected void a(float f, int i, int j) {
        int y;
        int ii;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = (this.g - this.c) / 2;
        int i1 = (this.h - this.d) / 2;
        this.b(l, i1, 0, 0, this.c, 6);
        if (!this.container.isAvailable()) {
            this.b(l, i1 + 6, 0, 6, this.c, 64);
            this.b(l, i1 + 70, 0, 124, this.c, 98);
            ii = this.p + 30;
            y = this.q + 8;
            this.o.b(bu.a((String)"bank.unlockCosts") + ":", ii, y + 4, 0x404040);
            this.drawItem(ii + 90, y, this.currency, i, j);
        } else if (this.container.isUpgraded()) {
            this.b(l, i1 + 60, 0, 60, this.c, 162);
            this.b(l, i1 + 6, 0, 60, this.c, 64);
        } else if (this.container.canBeUpgraded()) {
            this.b(l, i1 + 6, 0, 6, this.c, 216);
            ii = this.p + 30;
            y = this.q + 8;
            this.o.b(bu.a((String)"bank.upgradeCosts") + ":", ii, y + 4, 0x404040);
            this.drawItem(ii + 90, y, this.currency, i, j);
        } else {
            this.b(l, i1 + 6, 0, 60, this.c, 162);
        }
        if (this.maxSlots > 1) {
            for (ii = 0; ii < this.maxSlots && this.availableSlots != ii; ++ii) {
                this.o.b("Tab " + (ii + 1), this.p - 40, this.q + 16 + ii * 24, 0xFFFFFF);
            }
        }
        super.a(f, i, j);
    }

    private void drawItem(int x, int y, ye item, int mouseX, int mouseY) {
        if (item != null) {
            GL11.glEnable((int)32826);
            att.c();
            b.a(this.o, this.f.N, item, x, y);
            b.c(this.o, this.f.N, item, x, y);
            att.a();
            GL11.glDisable((int)32826);
            if (this.c(x - this.p, y - this.q, 16, 16, mouseX, mouseY)) {
                this.a(item, mouseX, mouseY);
            }
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setGuiData(by compound) {
        this.maxSlots = compound.e("MaxSlots");
        this.unlockedSlots = compound.e("UnlockedSlots");
        this.currency = compound.b("Currency") ? ye.a((by)compound.l("Currency")) : null;
        if (this.container.currency != null) {
            this.container.currency.item = this.currency;
        }
        this.A_();
    }
}


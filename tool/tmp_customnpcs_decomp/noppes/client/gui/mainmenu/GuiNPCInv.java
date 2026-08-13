/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  awe
 *  bgl
 *  bjo
 *  bma
 *  bu
 *  by
 *  com.adventurer_engine.util.DebugGadgets
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  nn
 *  org.lwjgl.opengl.GL11
 *  we
 */
package noppes.client.gui.mainmenu;

import com.adventurer_engine.util.DebugGadgets;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ISliderListener;
import noppes.common.containers.ContainerNPCInv;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditMainMenuPackets;
import org.lwjgl.opengl.GL11;

public class GuiNPCInv
extends GuiContainerNPCInterface2
implements ISliderListener,
IGuiData {
    private final HashMap<Integer, Integer> chances = new HashMap();
    private final ContainerNPCInv container;
    private final bjo slot;
    private static final ey MAINMENU_INV_GET = new EditMainMenuPackets.MainmenuInvGet().makePacket("CNPCs Server");

    public GuiNPCInv(EntityNPCInterface npc, ContainerNPCInv container) {
        super(npc, container, 3);
        DebugGadgets.debugInfo((String)"GuiNPCInv constructor called");
        this.setBackground("npcinv.png");
        this.container = container;
        this.d = 200;
        this.slot = this.getResource("slot.png");
        PacketDispatcher.sendPacketToServer((ey)MAINMENU_INV_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "inv.minExp", this.p + 118, this.q + 18, 0x404040));
        this.addTextField(new GuiNpcTextField(0, (awe)this, this.o, this.p + 108, this.q + 29, 60, 20, String.valueOf(this.npc.inventory.minExp)));
        this.getTextField((int)0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, Short.MAX_VALUE, 0);
        this.addLabel(new GuiNpcLabel(1, "inv.maxExp", this.p + 118, this.q + 52, 0x404040));
        this.addTextField(new GuiNpcTextField(1, (awe)this, this.o, this.p + 108, this.q + 63, 60, 20, String.valueOf(this.npc.inventory.maxExp)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, Short.MAX_VALUE, 0);
        this.addLabel(new GuiNpcLabel(2, "inv.npcInventory", this.p + 191, this.q + 5, 0x404040));
        this.addLabel(new GuiNpcLabel(3, "inv.inventory", this.p + 8, this.q + 101, 0x404040));
        for (int i = 0; i < 8; ++i) {
            int chance = 100;
            if (this.npc.inventory.dropchance.containsKey(i)) {
                chance = this.npc.inventory.dropchance.get(i);
            }
            if (chance <= 0 || chance > 100) {
                chance = 100;
            }
            this.chances.put(i, chance);
            GuiNpcSlider slider = new GuiNpcSlider((awe)this, i, this.p + 211, this.q + 15 + i * 22, null, bu.a((String)"inv.dropChance") + ": " + chance + "%", (float)chance / 100.0f);
            this.addSlider(slider);
        }
    }

    @Override
    protected void a(float f, int i, int j) {
        super.a(f, i, j);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.slot);
        for (int id = 4; id <= 6; ++id) {
            we slot = this.container.a(id);
            if (!slot.e()) continue;
            this.b(this.p + slot.h - 1, this.q + slot.i - 1, 0, 0, 20, 20);
        }
    }

    @Override
    public void a(int i, int j, float f) {
        int showname = this.npc.display.showName;
        this.npc.display.showName = 1;
        int l = this.p + 20;
        int i1 = this.h / 2 - 145;
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), (float)50.0f);
        float f1 = 150.0f / (float)this.npc.display.modelSize;
        GL11.glScalef((float)(-f1), (float)f1, (float)f1);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f2 = this.npc.aN;
        float f3 = this.npc.A;
        float f4 = this.npc.B;
        float f5 = (float)(l + 33) - (float)i;
        float f6 = (float)(i1 + 131 - 50) - (float)j;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        att.b();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(f6 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.npc.aN = (float)Math.atan(f5 / 40.0f) * 20.0f;
        this.npc.A = (float)Math.atan(f5 / 40.0f) * 40.0f;
        this.npc.B = -((float)Math.atan(f6 / 40.0f)) * 20.0f;
        this.npc.aP = this.npc.A;
        GL11.glTranslatef((float)0.0f, (float)this.npc.N, (float)0.0f);
        bgl.a.j = 180.0f;
        bgl.a.a((nn)this.npc, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.npc.aN = f2;
        this.npc.A = f3;
        this.npc.B = f4;
        GL11.glPopMatrix();
        att.a();
        GL11.glDisable((int)32826);
        bma.a((int)bma.b);
        GL11.glDisable((int)3553);
        bma.a((int)bma.a);
        this.npc.display.showName = showname;
        super.a(i, j, f);
    }

    @Override
    public void save() {
        this.npc.inventory.dropchance = this.chances;
        this.npc.inventory.minExp = this.getTextField(0).getInteger();
        this.npc.inventory.maxExp = this.getTextField(1).getInteger();
        ey pkt = new EditMainMenuPackets.MainmenuInvSave(this.npc.inventory.writeEntityToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void setGuiData(by compound) {
        this.npc.inventory.readEntityFromNBT(compound);
        this.A_();
    }

    @Override
    public void mouseDragged(GuiNpcSlider guiNpcSlider) {
        guiNpcSlider.f = bu.a((String)"inv.dropChance") + ": " + (int)(guiNpcSlider.o * 100.0f) + "%";
    }

    @Override
    public void mousePressed(GuiNpcSlider guiNpcSlider) {
    }

    @Override
    public void mouseReleased(GuiNpcSlider guiNpcSlider) {
        this.chances.put(guiNpcSlider.g, (int)(guiNpcSlider.o * 100.0f));
    }
}


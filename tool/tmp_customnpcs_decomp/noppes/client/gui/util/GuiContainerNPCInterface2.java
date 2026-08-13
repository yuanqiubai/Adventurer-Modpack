/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  atv
 *  aut
 *  avi
 *  awe
 *  awy
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 *  uf
 *  uy
 *  we
 */
package noppes.client.gui.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcMenu;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditDeletePacket;
import noppes.util.NoppesUtil;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerNPCInterface2
extends awy {
    private final bjo defaultBackground;
    public int p;
    public int q;
    public uf player;
    public boolean drawDefaultBackground = false;
    public EntityNPCInterface npc;
    private bjo background = new bjo("customnpcs", "textures/gui/menubg.png");
    private final GuiNpcMenu menu;
    private SubGuiInterface subgui;

    public GuiContainerNPCInterface2(EntityNPCInterface npc, uy cont) {
        this(npc, cont, -1);
    }

    public GuiContainerNPCInterface2(EntityNPCInterface npc, uy cont, int activeMenu) {
        super(cont);
        this.defaultBackground = new bjo("customnpcs", "textures/gui/menubg.png");
        this.player = atv.w().h;
        this.npc = npc;
        this.c = 420;
        this.menu = new GuiNpcMenu((awe)this, activeMenu, npc);
    }

    public void setBackground(String texture) {
        this.background = new bjo("customnpcs", "textures/gui/" + texture);
    }

    public bjo getResource(String texture) {
        return new bjo("customnpcs", "textures/gui/" + texture);
    }

    public void A_() {
        super.A_();
        if (this.subgui != null) {
            this.subgui.a(this.f, this.g, this.h);
            this.subgui.A_();
        }
        this.i.clear();
        this.p = (this.g - this.c) / 2;
        this.q = (this.h - 200) / 2;
        this.menu.initGui(this.p, this.q, this.c);
    }

    public void c() {
        this.menu.updateScreen();
        super.c();
    }

    protected void a(int i, int j, int k) {
        if (this.subgui != null) {
            this.subgui.a(i, j, k);
        } else {
            this.menu.mouseClicked(i, j, k);
            this.mouseEvent(i, j, k);
            super.a(i, j, k);
        }
    }

    public void mouseEvent(int i, int j, int k) {
    }

    protected void a(aut guibutton) {
        if (this.subgui != null) {
            this.subgui.buttonEvent(guibutton);
        } else {
            this.buttonEvent(guibutton);
        }
    }

    public void buttonEvent(aut guibutton) {
    }

    public void a(boolean flag, int i) {
        if (flag) {
            PacketDispatcher.sendPacketToServer((ey)new EditDeletePacket().makePacket("CNPCs Server"));
            this.f.a(null);
            this.f.g();
        } else {
            NoppesUtil.openGUI(this.player, (Object)this);
        }
    }

    public void a(char c, int i) {
        if (this.subgui != null) {
            this.subgui.a(c, i);
        } else {
            this.menu.keyTyped(c, i);
        }
    }

    public void close() {
        GuiNpcTextField.unfocus();
        this.save();
        this.f.a(null);
        this.f.g();
    }

    public void addButton(GuiNpcButton button) {
        this.menu.addButton(button);
        this.i.add(button);
    }

    public GuiNpcButton getButton(int i) {
        return this.menu.getButton(i);
    }

    public void addSlider(GuiNpcSlider slider) {
        this.menu.addSlider(slider);
        this.i.add(slider);
    }

    public GuiNpcSlider getSlider(int i) {
        return this.menu.getSlider(i);
    }

    public void addTextField(GuiNpcTextField tf) {
        this.menu.addTextField(tf);
    }

    public GuiNpcTextField getTextField(int i) {
        return this.menu.getTextField(i);
    }

    public void addLabel(GuiNpcLabel label) {
        this.menu.addLabel(label);
    }

    public GuiNpcLabel getLabel(int i) {
        return this.menu.getLabel(i);
    }

    public void delete() {
        this.npc.delete();
        this.f.a(null);
        this.f.g();
    }

    public void e() {
        if (this.drawDefaultBackground && this.subgui == null) {
            this.b(0);
        }
    }

    protected void a(float f, int i, int j) {
        this.e();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.background);
        this.b(this.p, this.q, 0, 0, 256, 256);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.defaultBackground);
        this.b(this.p + this.c - 200, this.q, 26, 0, 200, 220);
        this.menu.drawElements(this.o, i, j, this.f, f);
    }

    public abstract void save();

    public void a(int i, int j, float f) {
        super.a(i, j, f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (this.subgui != null) {
            att.a();
            this.subgui.a(i, j, f);
        }
    }

    protected void a(we par1Slot) {
        if (this.subgui == null) {
            super.a(par1Slot);
        }
    }

    public avi getFontRenderer() {
        return this.o;
    }

    public void closeSubGui(SubGuiInterface gui) {
        this.subgui = null;
    }

    public boolean hasSubGui() {
        return this.subgui != null;
    }

    public void setSubGuiData(by compound) {
        ((IGuiData)((Object)this.subgui)).setGuiData(compound);
    }

    public SubGuiInterface getSubGui() {
        return this.subgui;
    }

    public void setSubGui(SubGuiInterface gui) {
        this.subgui = gui;
        this.subgui.a(this.f, this.g, this.h);
        this.subgui.parent = this;
        this.A_();
    }
}


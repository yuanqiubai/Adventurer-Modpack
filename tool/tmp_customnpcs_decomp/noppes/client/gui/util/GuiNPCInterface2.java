/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aut
 *  avi
 *  awe
 *  bfq
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.gui.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcMenu;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditDeletePacket;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface2
extends awe {
    public int guiLeft;
    public int guiTop;
    public int guiWidth;
    public uf player;
    public boolean drawDefaultBackground = true;
    public EntityNPCInterface npc;
    private final bjo background = new bjo("customnpcs:textures/gui/menubg.png");
    private final GuiNpcMenu menu;
    private SubGuiInterface subgui;

    public GuiNPCInterface2(EntityNPCInterface npc) {
        this(npc, -1);
    }

    public GuiNPCInterface2(EntityNPCInterface npc, int activeMenu) {
        this.player = atv.w().h;
        this.npc = npc;
        this.guiWidth = 420;
        this.menu = new GuiNpcMenu(this, activeMenu, npc);
    }

    public void A_() {
        super.A_();
        if (this.subgui != null) {
            this.subgui.a(this.f, this.g, this.h);
            this.subgui.A_();
        }
        this.i.clear();
        this.guiLeft = (this.g - this.guiWidth) / 2;
        this.guiTop = (this.h - 200) / 2;
        this.menu.initGui(this.guiLeft, this.guiTop, this.guiWidth);
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

    public void clearAllTextFields() {
        this.menu.clearAllTextFields();
    }

    public void a(boolean flag, int i) {
        if (flag) {
            PacketDispatcher.sendPacketToServer((ey)new EditDeletePacket().makePacket("CNPCs Server"));
            this.f.a(null);
            this.f.g();
        } else {
            this.f.a((awe)this);
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

    public void addScroll(GuiCustomScroll scroll) {
        this.menu.addScroll(scroll, this.f);
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

    public abstract void save();

    public void a(int i, int j, float f) {
        if (this.drawDefaultBackground && this.subgui == null) {
            this.e();
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.background);
        this.b(this.guiLeft, this.guiTop, 0, 0, 200, 220);
        this.b(this.guiLeft + this.guiWidth - 230, this.guiTop, 26, 0, 230, 220);
        this.menu.drawElements(this.o, i, j, this.f, f);
        super.a(i, j, f);
        if (this.subgui != null) {
            this.subgui.a(i, j, f);
        }
    }

    public avi getFontRenderer() {
        return this.o;
    }

    public boolean drawSlot(int i, int j, int k, int l, bfq tessellator, String drawString) {
        return false;
    }

    public void elementClicked() {
        if (this.subgui != null) {
            this.subgui.elementClicked();
        }
    }

    public void doubleClicked() {
    }

    public boolean f() {
        return false;
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


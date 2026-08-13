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
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.gui.util;

import java.util.HashMap;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiMenuSideButton;
import noppes.client.gui.util.GuiMenuTopButton;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.entity.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface
extends awe {
    public uf player;
    public boolean drawDefaultBackground = true;
    public EntityNPCInterface npc;
    public String title;
    public boolean closeOnEsc = false;
    public int guiLeft;
    public int guiTop;
    public int xSize;
    public int ySize;
    private final HashMap<Integer, GuiNpcButton> buttons = new HashMap();
    private final HashMap<Integer, GuiMenuTopButton> topbuttons = new HashMap();
    private final HashMap<Integer, GuiMenuSideButton> sidebuttons = new HashMap();
    private final HashMap<Integer, GuiNpcTextField> textfields = new HashMap();
    private final HashMap<Integer, GuiNpcLabel> labels = new HashMap();
    private final HashMap<Integer, GuiCustomScroll> scrolls = new HashMap();
    private final HashMap<Integer, GuiNpcSlider> sliders = new HashMap();
    private bjo background = null;

    public GuiNPCInterface(EntityNPCInterface npc) {
        this.player = atv.w().h;
        this.npc = npc;
        this.title = "";
        this.xSize = 200;
        this.ySize = 222;
    }

    public GuiNPCInterface() {
        this(null);
    }

    public void setBackground(String texture) {
        this.background = new bjo("customnpcs", "textures/gui/" + texture);
    }

    public bjo getResource(String texture) {
        return new bjo("customnpcs", "textures/gui/" + texture);
    }

    public void A_() {
        this.guiLeft = (this.g - this.xSize) / 2;
        this.guiTop = (this.h - this.ySize) / 2;
        this.i.clear();
        this.labels.clear();
        this.textfields.clear();
        this.buttons.clear();
        this.sidebuttons.clear();
        this.topbuttons.clear();
        this.scrolls.clear();
        this.sliders.clear();
        Keyboard.enableRepeatEvents((boolean)true);
    }

    public void c() {
        for (GuiNpcTextField tf : this.textfields.values()) {
            if (!tf.enabled) continue;
            tf.a();
        }
        super.c();
    }

    public void a(int i, int j, int k) {
        for (GuiNpcTextField tf : this.textfields.values().toArray(new GuiNpcTextField[this.textfields.size()])) {
            if (!tf.enabled) continue;
            tf.a(i, j, k);
        }
        if (k == 0) {
            for (GuiCustomScroll var9 : this.scrolls.values()) {
                var9.a(i, j, k);
            }
        }
        super.a(i, j, k);
    }

    protected void a(aut par1GuiButton) {
        this.buttonEvent(par1GuiButton);
    }

    public void buttonEvent(aut guibutton) {
    }

    public void a(char c, int i) {
        for (GuiNpcTextField tf : this.textfields.values()) {
            tf.a(c, i);
        }
        if (this.closeOnEsc && i == 1) {
            this.close();
        }
    }

    public void close() {
        this.f.a(null);
        this.f.g();
        this.save();
    }

    public void addButton(int i, GuiNpcButton button) {
        this.buttons.put(i, button);
        this.i.add(button);
    }

    public void addButton(GuiNpcButton button) {
        this.buttons.put(button.g, button);
        this.i.add(button);
    }

    public void addTopButton(GuiMenuTopButton button) {
        this.topbuttons.put(button.g, button);
        this.i.add(button);
    }

    public void addSideButton(GuiMenuSideButton button) {
        this.sidebuttons.put(button.g, button);
        this.i.add(button);
    }

    public GuiNpcButton getButton(int i) {
        return this.buttons.get(i);
    }

    public GuiMenuSideButton getSideButton(int i) {
        return this.sidebuttons.get(i);
    }

    public void addTextField(int i, GuiNpcTextField tf) {
        this.textfields.put(i, tf);
    }

    public void addTextField(GuiNpcTextField tf) {
        this.textfields.put(tf.id, tf);
    }

    public GuiNpcTextField getTextField(int i) {
        return this.textfields.get(i);
    }

    public void addLabel(GuiNpcLabel label) {
        this.labels.put(label.id, label);
    }

    public GuiNpcLabel getLabel(int i) {
        return this.labels.get(i);
    }

    public void addScroll(GuiCustomScroll scroll, atv mc) {
        scroll.a(mc, 350, 250);
        this.scrolls.put(scroll.id, scroll);
    }

    public void addSlider(GuiNpcSlider slider) {
        this.sliders.put(slider.g, slider);
        this.i.add(slider);
    }

    public GuiNpcSlider getSlider(int i) {
        return this.sliders.get(i);
    }

    public GuiCustomScroll getScroll(int id) {
        return this.scrolls.get(id);
    }

    public abstract void save();

    public void a(int i, int j, float f) {
        if (this.drawDefaultBackground) {
            this.e();
        }
        if (this.background != null && this.f.N != null) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.f.N.a(this.background);
            this.b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        }
        this.a(this.o, this.title, this.g / 2, 10, 0xFFFFFF);
        for (GuiNpcLabel scroll : this.labels.values()) {
            scroll.drawLabel(this, this.o);
        }
        for (GuiNpcTextField scroll1 : this.textfields.values()) {
            scroll1.f();
        }
        for (GuiCustomScroll scroll2 : this.scrolls.values()) {
            scroll2.a(i, j, f);
        }
        super.a(i, j, f);
    }

    public avi getFontRenderer() {
        return this.o;
    }

    public boolean drawSlot(int i, int j, int k, int l, bfq tessellator, String drawString) {
        return false;
    }

    public void elementClicked() {
    }

    public boolean f() {
        return false;
    }

    public void doubleClicked() {
    }
}


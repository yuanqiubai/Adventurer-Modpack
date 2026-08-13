/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  avi
 *  awe
 *  awy
 *  bdi
 *  bjo
 *  org.lwjgl.input.Keyboard
 *  uy
 */
package noppes.client.gui.util;

import java.util.HashMap;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.entity.EntityNPCInterface;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainerNPCInterface
extends awy {
    public bdi player;
    public EntityNPCInterface npc;
    public String title;
    public boolean closeOnEsc = false;
    private final HashMap<Integer, GuiNpcButton> buttons = new HashMap();
    private final HashMap<Integer, GuiNpcTextField> textfields = new HashMap();
    private final HashMap<Integer, GuiNpcLabel> labels = new HashMap();

    public GuiContainerNPCInterface(EntityNPCInterface npc, uy cont) {
        super(cont);
        this.player = atv.w().h;
        this.npc = npc;
        this.title = "Npc Mainmenu";
    }

    public void A_() {
        super.A_();
        this.i.clear();
        this.buttons.clear();
        Keyboard.enableRepeatEvents((boolean)true);
    }

    public bjo getResource(String texture) {
        return new bjo("customnpcs", "textures/gui/" + texture);
    }

    public void c() {
        for (GuiNpcTextField tf : this.textfields.values()) {
            if (!tf.enabled) continue;
            tf.a();
        }
        super.c();
    }

    protected void a(int i, int j, int k) {
        for (GuiNpcTextField tf : this.textfields.values()) {
            if (!tf.enabled) continue;
            tf.a(i, j, k);
        }
        super.a(i, j, k);
    }

    protected void a(char c, int i) {
        for (GuiNpcTextField tf : this.textfields.values()) {
            tf.a(c, i);
        }
        if (this.closeOnEsc && (i == 1 || i == this.f.u.N.d)) {
            this.close();
        }
    }

    public void close() {
        this.save();
        this.player.i();
        this.f.a(null);
        this.f.g();
    }

    public void addButton(GuiNpcButton button) {
        this.buttons.put(button.g, button);
        this.i.add(button);
    }

    public GuiNpcButton getButton(int i) {
        return this.buttons.get(i);
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

    protected void b(int par1, int par2) {
    }

    protected void a(float f, int i, int j) {
        this.a(this.o, this.title, this.g / 2, 10, 0xFFFFFF);
        for (GuiNpcLabel tf : this.labels.values()) {
            tf.drawLabel((awe)this, this.o);
        }
        for (GuiNpcTextField tf1 : this.textfields.values()) {
            tf1.f();
        }
    }

    public abstract void save();

    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    public avi getFontRenderer() {
        return this.o;
    }
}


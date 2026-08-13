/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bjo
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.player;

import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.common.containers.ContainerCarpentryBench;
import org.lwjgl.opengl.GL11;

public class GuiNpcCarpentryBench
extends GuiContainerNPCInterface {
    private final bjo resource = new bjo("customnpcs", "textures/gui/anvil.png");
    private final ContainerCarpentryBench container;

    public GuiNpcCarpentryBench(ContainerCarpentryBench container) {
        super(null, container);
        this.container = container;
        this.title = "";
        this.j = false;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
    }

    public void a(aut guibutton) {
        super.a(guibutton);
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = (this.g - this.c) / 2;
        int i1 = (this.h - this.d) / 2;
        this.b(l, i1, 0, 0, this.c, this.d);
        super.a(f, i, j);
    }

    @Override
    public void save() {
    }
}


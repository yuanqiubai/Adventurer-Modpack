/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aut
 *  awy
 *  bjo
 *  bkb
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 *  org.lwjgl.opengl.GL11
 *  uy
 */
package com.adventurer_engine.client.gui;

import com.adventurer_engine.network.client2server.RequestCollateBackpack;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GuiBackpackContainer
extends awy {
    private static final bjo texture = new bjo("adventurer_engine", "textures/gui/container/backpack.png");
    private static final ey REQUEST_COLLATE = new RequestCollateBackpack().makePacket();

    public GuiBackpackContainer(uy par1Container) {
        super(par1Container);
        this.c = 248;
        this.d = 239;
    }

    protected void b(int i, int j) {
        super.b(i, j);
    }

    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.J().a(texture);
        int offsetX = (this.g - this.c) / 2;
        int offsetY = (this.h - this.d) / 2;
        this.b(offsetX, offsetY, 0, 0, this.c, this.d);
    }

    public void A_() {
        super.A_();
        int offsetX = (this.g - this.c) / 2;
        int offsetY = (this.h - this.d) / 2;
        this.i.add(new CollateButton(offsetX + 250, offsetY + 4));
    }

    protected void a(aut button) {
        if (button.g == 1) {
            PacketDispatcher.sendPacketToServer((ey)REQUEST_COLLATE);
        } else {
            super.a(button);
        }
    }

    private class CollateButton
    extends aut {
        private final bjo TEXTURE;

        public CollateButton(int posX, int posY) {
            super(1, posX, posY, 20, 20, "");
            this.TEXTURE = new bjo("adventurer_engine:textures/gui/button.png");
        }

        public void a(atv mc, int mouseX, int mouseY) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            mc.J().a(this.TEXTURE);
            int x = mouseX - this.d;
            int y = mouseY - this.e;
            if (x >= 0 && y >= 0 && x < this.b && y < this.c) {
                this.b(this.d, this.e, 0, 40, this.b, this.c);
            } else {
                this.b(this.d, this.e, 0, 20, this.b, this.c);
            }
            mc.l.a(bkb.a((String)"adv.collate"), this.d + 2, this.e + 6, 0xFFFFFF, false);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aun
 *  awe
 *  awk
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.util;

import noppes.client.gui.util.ISliderListener;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNpcSlider
extends awk {
    private ISliderListener listener;

    public GuiNpcSlider(awe parent, int id, int xPos, int yPos, aun idFloat, String displayString, float sliderValue) {
        super(id, xPos, yPos, idFloat, displayString, sliderValue);
        if (parent instanceof ISliderListener) {
            this.listener = (ISliderListener)parent;
        }
    }

    protected void b(atv par1Minecraft, int par2, int par3) {
        if (this.i) {
            if (this.p) {
                this.o = (float)(par2 - (this.d + 4)) / (float)(this.b - 8);
                if (this.o < 0.0f) {
                    this.o = 0.0f;
                }
                if (this.o > 1.0f) {
                    this.o = 1.0f;
                }
                if (this.listener != null) {
                    this.listener.mouseDragged(this);
                }
                if (!Mouse.isButtonDown((int)0)) {
                    this.a(0, 0);
                }
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.b(this.d + (int)(this.o * (float)(this.b - 8)), this.e, 0, 66, 4, 20);
            this.b(this.d + (int)(this.o * (float)(this.b - 8)) + 4, this.e, 196, 66, 4, 20);
        }
    }

    public boolean c(atv par1Minecraft, int par2, int par3) {
        if (this.h && this.i && par2 >= this.d && par3 >= this.e && par2 < this.d + this.b && par3 < this.e + this.c) {
            this.o = (float)(par2 - (this.d + 4)) / (float)(this.b - 8);
            if (this.o < 0.0f) {
                this.o = 0.0f;
            }
            if (this.o > 1.0f) {
                this.o = 1.0f;
            }
            if (this.listener != null) {
                this.listener.mousePressed(this);
            }
            this.p = true;
            return true;
        }
        return false;
    }

    public void a(int par1, int par2) {
        this.p = false;
        if (this.listener != null) {
            this.listener.mouseReleased(this);
        }
    }
}


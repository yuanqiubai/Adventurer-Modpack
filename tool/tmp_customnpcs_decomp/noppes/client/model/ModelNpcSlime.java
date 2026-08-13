/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ModelNpcSlime
extends bbo {
    bcu outerBody;
    bcu innerBody;
    bcu slimeRightEye;
    bcu slimeLeftEye;
    bcu slimeMouth;

    public ModelNpcSlime(int par1) {
        this.u = 64;
        this.t = 64;
        this.outerBody = new bcu((bbo)this, 0, 0);
        this.outerBody = new bcu((bbo)this, 0, 0);
        this.outerBody.a(-8.0f, 32.0f, -8.0f, 16, 16, 16);
        if (par1 > 0) {
            this.innerBody = new bcu((bbo)this, 0, 32);
            this.innerBody.a(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            this.slimeRightEye = new bcu((bbo)this, 0, 0);
            this.slimeRightEye.a(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeLeftEye = new bcu((bbo)this, 0, 4);
            this.slimeLeftEye.a(1.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeMouth = new bcu((bbo)this, 0, 8);
            this.slimeMouth.a(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }

    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.a(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.innerBody != null) {
            this.innerBody.a(par7);
        } else {
            GL11.glPushMatrix();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            this.outerBody.a(par7);
            GL11.glPopMatrix();
        }
        if (this.slimeRightEye != null) {
            this.slimeRightEye.a(par7);
            this.slimeLeftEye.a(par7);
            this.slimeMouth.a(par7);
        }
    }
}


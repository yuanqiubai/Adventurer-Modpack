/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  asp
 *  bje
 *  bjo
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.renderer;

import noppes.client.model.ModelCarpentryBench;
import org.lwjgl.opengl.GL11;

public class BlockCarpentryBenchRenderer
extends bje {
    private static final bjo RES_NORMAL_SINGLE = new bjo("customnpcs", "textures/misc/CarpentryBench.png");
    private final ModelCarpentryBench model = new ModelCarpentryBench();

    public void a(asp var1, double var2, double var4, double var6, float var8) {
        int meta = var1.k.h(var1.l, var1.m, var1.n);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2 + 0.5f), (float)((float)var4 + 1.4f), (float)((float)var6 + 0.5f));
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)(90 * meta), (float)0.0f, (float)1.0f, (float)0.0f);
        this.a(RES_NORMAL_SINGLE);
        this.model.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}


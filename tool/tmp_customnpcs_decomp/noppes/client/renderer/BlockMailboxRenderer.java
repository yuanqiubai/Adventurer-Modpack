/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  acf
 *  aqz
 *  asp
 *  bfr
 *  bje
 *  bjo
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import noppes.client.model.ModelMailboxUS;
import noppes.client.model.ModelMailboxWow;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class BlockMailboxRenderer
extends bje
implements ISimpleBlockRenderingHandler {
    private static final bjo text1 = new bjo("customnpcs", "textures/misc/mailbox1.png");
    private static final bjo text2 = new bjo("customnpcs", "textures/misc/mailbox2.png");
    private final ModelMailboxUS model = new ModelMailboxUS();
    private final ModelMailboxWow model2 = new ModelMailboxWow();

    public void a(asp var1, double var2, double var4, double var6, float var8) {
        int meta = var1.k.h(var1.l, var1.m, var1.n) | 4;
        int type = var1.k.h(var1.l, var1.m, var1.n) >> 2;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2 + 0.5f), (float)((float)var4 + 1.5f), (float)((float)var6 + 0.5f));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)(90 * meta), (float)0.0f, (float)1.0f, (float)0.0f);
        if (type == 0) {
            this.a(text1);
            this.model.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        if (type == 1) {
            this.a(text2);
            this.model2.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }

    public void renderInventoryBlock(aqz block, int metadata, int modelID, bfr renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.8f, (float)0.0f);
        GL11.glScalef((float)0.9f, (float)0.9f, (float)0.9f);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        if (metadata == 0) {
            this.a(text1);
            this.model.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        if (metadata == 1) {
            this.a(text2);
            this.model2.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(acf world, int x, int y, int z, aqz block, int modelId, bfr renderer) {
        return false;
    }

    public boolean shouldRender3DInInventory() {
        return true;
    }

    public int getRenderId() {
        return CustomItems.mailbox.d();
    }
}


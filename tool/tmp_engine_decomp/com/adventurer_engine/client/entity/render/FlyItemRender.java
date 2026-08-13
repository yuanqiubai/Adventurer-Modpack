/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.util.Icon
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FlyItemRender
extends Render {
    public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (!(par1Entity instanceof EntityBulletModelItem)) {
            return;
        }
        EntityBulletModelItem bullet = (EntityBulletModelItem)par1Entity;
        int i = bullet.renderItemID;
        if (i == 0) {
            return;
        }
        Icon icon = Item.field_77698_e[i].func_77617_a(0);
        if (icon != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
            GL11.glEnable((int)32826);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            this.func_110777_b(par1Entity);
            Tessellator tessellator = Tessellator.field_78398_a;
            this.renderItem(tessellator, icon);
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation func_110775_a(Entity entity) {
        return TextureMap.field_110576_c;
    }

    private void renderItem(Tessellator par1Tessellator, Icon par2Icon) {
        float f = par2Icon.func_94209_e();
        float f1 = par2Icon.func_94212_f();
        float f2 = par2Icon.func_94206_g();
        float f3 = par2Icon.func_94210_h();
        float f4 = 1.0f;
        float f5 = 0.5f;
        float f6 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.field_76990_c.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.field_76990_c.field_78732_j), (float)1.0f, (float)0.0f, (float)0.0f);
        par1Tessellator.func_78382_b();
        par1Tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
        par1Tessellator.func_78374_a((double)(0.0f - f5), (double)(0.0f - f6), 0.0, (double)f, (double)f3);
        par1Tessellator.func_78374_a((double)(f4 - f5), (double)(0.0f - f6), 0.0, (double)f1, (double)f3);
        par1Tessellator.func_78374_a((double)(f4 - f5), (double)(f4 - f6), 0.0, (double)f1, (double)f2);
        par1Tessellator.func_78374_a((double)(0.0f - f5), (double)(f4 - f6), 0.0, (double)f, (double)f2);
        par1Tessellator.func_78381_a();
    }
}


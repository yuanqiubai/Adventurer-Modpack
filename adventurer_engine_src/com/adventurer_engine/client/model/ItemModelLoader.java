/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bjo
 *  com.google.gson.internal.StringMap
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 *  ss
 *  yc
 *  ye
 */
package com.adventurer_engine.client.model;

import com.adventurer_engine.client.model.ItemModelBaked;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.items.blank.Full3DItem;
import com.google.gson.internal.StringMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ItemModelLoader
implements IItemRenderer {
    public static final StringMap<ItemModelBaked> modelMap = new StringMap();
    public static float smoothing;

    public static void loadModel() {
        for (yc i : ItemLoader.item) {
            if (!(i instanceof Full3DItem) || !((Full3DItem)i).hasModel()) continue;
            String modelName = ((Full3DItem)i).getModelName();
            ItemModelBaked bakedModel = new ItemModelBaked(new bjo("adventurer_engine:model/" + modelName + ".json"), new bjo("adventurer_engine:textures/model/" + modelName + ".png"));
            modelMap.put(((Full3DItem)i).getModelName(), (Object)bakedModel);
        }
    }

    public boolean handleRenderType(ye item, IItemRenderer.ItemRenderType type) {
        return type != IItemRenderer.ItemRenderType.INVENTORY;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ye item, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ye item, Object ... data) {
        if (!(item.b() instanceof Full3DItem)) {
            return;
        }
        ItemModelBaked model = (ItemModelBaked)((Object)modelMap.get((Object)((Full3DItem)item.b()).getModelName()));
        if (model == null) {
            return;
        }
        float f = 0.0625f;
        atv.w().N.a(model.texture);
        float[] entityData = new float[]{-0.5f, 0.0f, -0.5f, 1.0f, 1.0f, 1.0f};
        float[] equippedData = new float[]{0.0f, 195.5f, 0.0f, -0.85f, 0.1f, -1.0f, 1.0f, 1.0f, 1.0f};
        float[] firstPersonData = new float[]{-25.0f, -112.0f, -23.0f, -0.8f, 0.0f, -1.2f, 1.0f, 1.0f, 1.0f};
        GL11.glPushMatrix();
        switch (type) {
            case ENTITY: {
                ss entity = (ss)data[1];
                GL11.glRotatef((float)(((float)entity.a + (entity.a == 0 ? 0.0f : smoothing)) * 20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)entityData[0], (float)entityData[1], (float)entityData[2]);
                GL11.glScalef((float)entityData[3], (float)entityData[4], (float)entityData[5]);
                break;
            }
            case EQUIPPED: {
                GL11.glRotatef((float)(-equippedData[0]), (float)1.0f, (float)0.0f, (float)0.0f);
                float vx = 0.0f;
                float vy = -ItemModelLoader.cos(equippedData[0]);
                float vz = -ItemModelLoader.sin(equippedData[0]);
                GL11.glRotatef((float)(-equippedData[1]), (float)vx, (float)vy, (float)vz);
                vx = -ItemModelLoader.cos(equippedData[0]) * ItemModelLoader.sin(equippedData[1]);
                vy = -ItemModelLoader.sin(equippedData[0]);
                vz = ItemModelLoader.cos(equippedData[0]) * ItemModelLoader.cos(equippedData[1]);
                GL11.glRotatef((float)(-equippedData[2]), (float)vx, (float)vy, (float)vz);
                GL11.glTranslatef((float)equippedData[3], (float)equippedData[4], (float)equippedData[5]);
                GL11.glScalef((float)equippedData[6], (float)equippedData[7], (float)equippedData[8]);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glRotatef((float)(-firstPersonData[0]), (float)1.0f, (float)0.0f, (float)0.0f);
                float vx = 0.0f;
                float vy = -ItemModelLoader.cos(firstPersonData[0]);
                float vz = -ItemModelLoader.sin(firstPersonData[0]);
                GL11.glRotatef((float)(-firstPersonData[1]), (float)vx, (float)vy, (float)vz);
                vx = -ItemModelLoader.cos(firstPersonData[0]) * ItemModelLoader.sin(firstPersonData[1]);
                vy = -ItemModelLoader.sin(firstPersonData[0]);
                vz = ItemModelLoader.cos(firstPersonData[0]) * ItemModelLoader.cos(firstPersonData[1]);
                GL11.glRotatef((float)(-firstPersonData[2]), (float)vx, (float)vy, (float)vz);
                GL11.glTranslatef((float)firstPersonData[3], (float)firstPersonData[4], (float)firstPersonData[5]);
                GL11.glScalef((float)firstPersonData[6], (float)firstPersonData[7], (float)firstPersonData[8]);
            }
        }
        if (type != IItemRenderer.ItemRenderType.INVENTORY) {
            GL11.glPushMatrix();
            GL11.glScalef((float)model.modelScale, (float)model.modelScale, (float)model.modelScale);
            model.renderItem(f);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    private static float sin(float a) {
        return (float)Math.sin((double)(a / 180.0f) * Math.PI);
    }

    private static float cos(float a) {
        return (float)Math.cos((double)(a / 180.0f) * Math.PI);
    }
}


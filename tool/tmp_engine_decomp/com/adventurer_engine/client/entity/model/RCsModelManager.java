/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.ResourceManager
 *  net.minecraft.util.ResourceLocation
 */
package com.adventurer_engine.client.entity.model;

import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.blockbench.BBModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

public class RCsModelManager {
    public static BBModel model;

    private static BBModel modelSeeker(String name) {
        ResourceLocation fufuModel = new ResourceLocation("adventurer_engine" + String.format(":model/%s.json", name));
        ResourceManager s = Minecraft.func_71410_x().func_110442_L();
        try {
            JsonHelper json = new JsonHelper(s.func_110536_a(fufuModel).func_110527_b());
            return new BBModel(json);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void addModel() {
        model = RCsModelManager.modelSeeker("fufu");
    }
}


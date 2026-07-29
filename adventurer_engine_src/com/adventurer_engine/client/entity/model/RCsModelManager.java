/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bjo
 *  bjp
 */
package com.adventurer_engine.client.entity.model;

import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.blockbench.BBModel;

public class RCsModelManager {
    public static BBModel model;

    private static BBModel modelSeeker(String name) {
        bjo fufuModel = new bjo("adventurer_engine" + String.format(":model/%s.json", name));
        bjp s = atv.w().K();
        try {
            JsonHelper json = new JsonHelper(s.a(fufuModel).b());
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


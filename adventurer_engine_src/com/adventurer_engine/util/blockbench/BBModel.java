/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.StringMap
 */
package com.adventurer_engine.util.blockbench;

import com.adventurer_engine.util.IReadJsonable;
import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.blockbench.BBModelAnimation;
import com.adventurer_engine.util.blockbench.BBModelElement;
import com.adventurer_engine.util.blockbench.BBModelMetaData;
import com.adventurer_engine.util.blockbench.BBModelOutliner;
import com.adventurer_engine.util.blockbench.BBModelTexture;
import com.google.gson.internal.StringMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BBModel
implements IReadJsonable {
    public static Map<String, BBModelElement> elementMap = new TreeMap<String, BBModelElement>();
    public static Map<String, BBModelOutliner> outlinerMap = new TreeMap<String, BBModelOutliner>();
    public static Map<String, BBModelAnimation> animationMap = new TreeMap<String, BBModelAnimation>();
    public BBModelMetaData meta;
    public String name;
    public String model_identifier;
    public double[] visible_box;
    public BBModelTexture texture;
    public BBModelElement[] elements;
    public BBModelOutliner[] outliners;
    public StringMap<BBModelAnimation> animations;

    public BBModel(JsonHelper json) {
        this.readJson(json);
    }

    @Override
    public void readJson(JsonHelper json) {
        int i;
        this.meta = new BBModelMetaData(json.getSubJson("meta"));
        this.name = json.getString("name");
        this.model_identifier = json.getString("model_identifier");
        this.visible_box = json.getDoubleArray("visible_box");
        JsonHelper resolution = json.getSubJson("resolution");
        int width = resolution.getInt("width");
        int height = resolution.getInt("height");
        JsonHelper textures = json.getJsonList("textures").get(0);
        String uuid = textures.getString("uuid");
        String source = textures.getString("source");
        String name = textures.getString("name");
        this.texture = new BBModelTexture(width, height, uuid, source, name);
        List<JsonHelper> ls2 = json.getJsonList("elements");
        this.elements = new BBModelElement[ls2.size()];
        for (i = 0; i < ls2.size(); ++i) {
            this.elements[i] = new BBModelElement(ls2.get(i));
        }
        ls2 = json.getJsonList("outliner");
        this.outliners = new BBModelOutliner[ls2.size()];
        for (i = 0; i < ls2.size(); ++i) {
            this.outliners[i] = new BBModelOutliner(ls2.get(i));
        }
        if (json.hasKey("animations")) {
            ls2 = json.getJsonList("animations");
            this.animations = new StringMap();
            for (JsonHelper l : ls2) {
                BBModelAnimation animation = new BBModelAnimation(l);
                this.animations.put(animation.name, (Object)animation);
            }
        }
    }
}


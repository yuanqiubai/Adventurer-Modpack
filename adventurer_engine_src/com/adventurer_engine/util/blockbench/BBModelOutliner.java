/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.blockbench;

import com.adventurer_engine.util.IReadJsonable;
import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.blockbench.BBModel;
import com.adventurer_engine.util.blockbench.BBModelElement;
import java.util.LinkedList;
import java.util.List;

public class BBModelOutliner
implements IReadJsonable {
    public String name;
    public double[] origin;
    public double[] rotation;
    public int color;
    public String uuid;
    public boolean export;
    public boolean mirror_uv;
    public boolean isOpen;
    public boolean locked;
    public boolean visibility;
    public List<BBModelElement> children_element;
    public List<BBModelOutliner> children_outliner;

    public BBModelOutliner(JsonHelper json) {
        this.readJson(json);
        BBModel.outlinerMap.put(this.uuid, this);
    }

    @Override
    public void readJson(JsonHelper json) {
        double[] dArray;
        double[] dArray2;
        this.name = json.getString("name");
        if (json.hasKey("origin")) {
            dArray2 = json.getDoubleArray("origin");
        } else {
            double[] dArray3 = new double[3];
            dArray3[0] = 0.0;
            dArray3[1] = 0.0;
            dArray2 = dArray3;
            dArray3[2] = 0.0;
        }
        this.origin = dArray2;
        if (json.hasKey("rotation")) {
            dArray = json.getDoubleArray("rotation");
        } else {
            double[] dArray4 = new double[3];
            dArray4[0] = 0.0;
            dArray4[1] = 0.0;
            dArray = dArray4;
            dArray4[2] = 0.0;
        }
        this.rotation = dArray;
        this.color = json.getInt("color");
        this.uuid = json.getString("uuid");
        this.export = json.getBoolean("export");
        this.mirror_uv = json.hasKey("mirror_uv") && json.getBoolean("mirror_uv");
        this.isOpen = json.hasKey("isOpen") && json.getBoolean("isOpen");
        this.locked = json.hasKey("locked") && json.getBoolean("locked");
        this.visibility = json.hasKey("visibility") && json.getBoolean("visibility");
        List<String> childrenUUID = json.getStringArray("children");
        this.children_element = new LinkedList<BBModelElement>();
        for (String uid : childrenUUID) {
            this.children_element.add(BBModel.elementMap.get(uid));
        }
        List<JsonHelper> childrenOutliner = json.getJsonList("children");
        this.children_outliner = new LinkedList<BBModelOutliner>();
        for (JsonHelper jh : childrenOutliner) {
            this.children_outliner.add(new BBModelOutliner(jh));
        }
    }
}


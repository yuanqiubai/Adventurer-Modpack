/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.util.blockbench;

import com.adventurer_engine.util.INBTable;
import com.adventurer_engine.util.JsonHelper;

public class BBModelMetaData
implements INBTable {
    public String format_version;
    public String model_format;
    public boolean box_uv;

    public BBModelMetaData(JsonHelper json) {
        this.readJson(json);
    }

    public void readJson(JsonHelper json) {
        this.format_version = json.getString("format_version");
        this.model_format = json.getString("model_format");
        this.box_uv = json.getBoolean("box_uv");
    }

    @Override
    public void readNBT(by nbt) {
        this.format_version = nbt.i("format_version");
        this.model_format = nbt.i("model_format");
        this.box_uv = nbt.n("box_uv");
    }

    @Override
    public by writeNBT() {
        by nbt = new by();
        nbt.a("format_version", this.format_version);
        nbt.a("model_format", this.model_format);
        nbt.a("box_uv", this.box_uv);
        return nbt;
    }
}


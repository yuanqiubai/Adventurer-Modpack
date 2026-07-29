/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.StringMap
 */
package com.adventurer_engine.common.entity.neo_npc;

import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.google.gson.internal.StringMap;
import java.util.Map;

public class NPCTempDataTable {
    private Map<String, Integer> customVar;
    private EntityRCsNPCBase host;

    private void initCustomVar() {
        this.customVar = new StringMap();
    }

    public void setCustomVar(String key, int value) {
        if (this.customVar == null) {
            this.initCustomVar();
        }
        this.customVar.put(key, value);
    }

    public int getCustomVar(String key) {
        if (this.customVar == null) {
            this.initCustomVar();
            return 0;
        }
        return this.customVar.get(key);
    }
}


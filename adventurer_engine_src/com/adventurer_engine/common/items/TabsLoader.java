/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  com.google.gson.internal.StringMap
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  ww
 *  yc
 *  zl
 */
package com.adventurer_engine.common.items;

import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabsLoader {
    public static final ww GADGETS = new ww("ade_gadgets"){

        public yc d() {
            return yc.g[zl.s.cv];
        }
    };
    public static final StringMap<ww> tabs = new StringMap();
    public static final Map<Integer, ww> numTabs = new HashMap<Integer, ww>();

    public static void load(FMLPreInitializationEvent event) {
    }

    public static ww getTab(String name) {
        return (ww)tabs.get((Object)name);
    }

    public static ww getTab(int id) {
        return numTabs.get(id);
    }

    public static void load(by nbt) {
        if (nbt.b("ItemGroup")) {
            cg ls2 = nbt.m("ItemGroup");
            for (int i = 0; i < ls2.c(); ++i) {
                cl tmp = ls2.b(i);
                if (!(tmp instanceof by)) continue;
                by group = (by)tmp;
                String tabName = group.i("ItemGroupName");
                final int iconID = group.e("IconItemID");
                int groupID = group.e("ItemGroupID");
                numTabs.put(groupID, new ww(tabName){

                    public yc d() {
                        return yc.g[iconID];
                    }
                });
            }
        }
    }

    private static void loadJson(FMLPreInitializationEvent event) {
        JsonHelper json = new JsonHelper(new File(event.getModConfigurationDirectory().getParentFile().getPath() + File.separator + "RCS" + File.separator + "index.json"));
        List tabDatas = json.getList("tabs");
        for (StringMap data : tabDatas) {
            int iconID;
            JsonHelper tabData = new JsonHelper(data);
            String tabName = tabData.getString("name");
            final int finalIconID = iconID = tabData.getInt("icon");
            ww tab = new ww(tabName){

                public yc d() {
                    return yc.g[finalIconID];
                }
            };
            tabs.put(tabName, (Object)tab);
        }
    }
}


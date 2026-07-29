/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bkb
 *  by
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ms
 *  mt
 *  nn
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common.items.blank;

import com.adventurer_engine.common.items.TabsLoader;
import com.adventurer_engine.common.items.blank.Full3DItem;
import com.adventurer_engine.util.JsonHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;

public class BasicItem
extends yc
implements Full3DItem {
    public final String icon;
    public final String model;
    public final String description;
    public final by default_nbt;
    public final String internal_name;

    public BasicItem(int id, JsonHelper json, String tab) {
        super(id);
        this.a(TabsLoader.getTab(tab));
        this.internal_name = json.getString("name");
        this.b(this.internal_name);
        this.icon = json.getString("icon");
        this.model = json.hasKey("model") ? json.getString("model") : null;
        this.description = json.hasKey("description") ? json.getString("description") : null;
        this.default_nbt = json.hasKey("default_nbt") ? json.getSubJson("default_nbt").toNBT() : null;
    }

    public BasicItem(by nbt) {
        super(nbt.e("item_ID"));
        this.a(TabsLoader.getTab(nbt.e("item_group")));
        this.internal_name = nbt.i("item_internalName");
        this.b(this.internal_name);
        this.icon = nbt.i("item_icon");
        this.cw = nbt.e("item_maxstack");
        this.description = nbt.b("item_description") ? nbt.i("item_description") : "";
        this.model = nbt.b("item_model") ? nbt.i("item_model") : null;
        this.default_nbt = nbt.b("item_default_nbt") ? nbt.l("item_defaultNBT") : null;
    }

    public void a(ye itemStack, uf player, List list, boolean par4) {
        if (this.description != null && !this.description.isEmpty()) {
            String[] desc = bkb.a((String)this.description).split("\\\\n");
            Collections.addAll(list, desc);
        }
    }

    public void a(ye item, abw world, nn entity, int par4, boolean par5) {
        if (this.default_nbt != null && !item.p()) {
            item.d(this.default_nbt);
        }
    }

    @Override
    public String getModelName() {
        return this.model;
    }

    @Override
    public boolean hasModel() {
        return this.model != null;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt register) {
        this.cz = register.a("adventurer_engine:" + this.icon);
    }

    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.cz;
    }
}


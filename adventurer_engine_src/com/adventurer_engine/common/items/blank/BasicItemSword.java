/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bkb
 *  by
 *  com.google.common.collect.Multimap
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  of
 *  ot
 *  tp
 *  uf
 *  ye
 *  zj
 */
package com.adventurer_engine.common.items.blank;

import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.util.JsonHelper;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

public class BasicItemSword
extends BasicItem {
    public final int duration;
    public final boolean unbreakable;
    public final float basic_damage;
    public final float ice_damage;
    public final DamagePacket onAttack;

    public BasicItemSword(int id, JsonHelper json, String tab) {
        super(id, json, tab);
        this.duration = json.hasKey("duration") ? json.getInt("duration") : 1;
        this.unbreakable = json.hasKey("unbreakable") && json.getBoolean("unbreakable");
        this.basic_damage = json.getFloat("basic_damage");
        this.onAttack = json.hasKey("onAttack") ? new DamagePacket(json.getSubJson("onAttack")) : null;
        this.cw = 1;
        this.ice_damage = 0.0f;
        this.e(this.duration);
    }

    public BasicItemSword(by nbt) {
        super(nbt.l("theItem"));
        this.duration = nbt.b("duration") ? nbt.e("duration") : 1;
        this.unbreakable = nbt.b("unbreakable") && nbt.d("unbreakable") == 1;
        this.basic_damage = (float)nbt.h("basic_damage");
        this.ice_damage = nbt.b("ice_damage") ? (float)nbt.h("ice_damage") : 0.0f;
        this.onAttack = nbt.b("attack_packet") ? DamagePacket.getPacket(nbt.e("attack_packet")) : null;
    }

    public boolean a(ye item, of wounded, of attacker) {
        if (!this.unbreakable) {
            item.a(1, attacker);
        }
        if (this.onAttack != null && attacker instanceof uf) {
            this.onAttack.causeDamage((uf)attacker, wounded, 0.0f, this.ice_damage, null);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean n_() {
        return true;
    }

    public zj c_(ye item) {
        return zj.d;
    }

    public int d_(ye item) {
        return 72000;
    }

    public ye a(ye item, abw world, uf player) {
        player.a(item, this.d_(item));
        return item;
    }

    public Multimap h() {
        Multimap multimap = super.h();
        multimap.put((Object)tp.e.a(), (Object)new ot(e, "Weapon modifier", (double)this.basic_damage, 0));
        return multimap;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        super.a(itemStack, player, list, par4);
        if (this.unbreakable) {
            list.add(bkb.a((String)"adv.unbreakable"));
        }
    }
}


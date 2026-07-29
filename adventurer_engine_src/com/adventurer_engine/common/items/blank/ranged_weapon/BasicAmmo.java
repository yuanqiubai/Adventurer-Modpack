/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bkb
 *  by
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common.items.blank.ranged_weapon;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.gadgets.RangeAttackPacket;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.util.JsonHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import org.lwjgl.input.Keyboard;

public class BasicAmmo
extends BasicItem {
    public final float basic_damage;
    public final float ice_damage;
    public final int entity_num;
    public final float entity_speed;
    public final float maxRange;
    public final boolean gravity;
    public final boolean unbreakable;
    public final boolean penetrate;
    public final boolean noeff;
    public final String fly_fx;
    public final String hit_fx;
    public final String entity_model;
    public final ShootMode shoot_mode;
    public final float accuracy;
    public final int shootDelay;
    public final float knockback;
    public final int charge_time;
    public final DamagePacket onAttack;
    public final String shoot_sound;
    public final int maxAmmoAmount;

    public BasicAmmo(int id, JsonHelper json, String tab) {
        super(id, json, tab);
        this.maxAmmoAmount = json.hasKey("duration") ? json.getInt("duration") : 1;
        this.e(this.maxAmmoAmount);
        this.basic_damage = json.hasKey("basic_damage") ? json.getFloat("basic_damage") : 0.0f;
        this.entity_num = json.hasKey("entity_num") ? json.getInt("entity_num") : 1;
        this.entity_speed = json.hasKey("entity_speed") ? json.getFloat("entity_speed") : 2.0f;
        this.gravity = !json.hasKey("gravity") || json.getBoolean("gravity");
        this.fly_fx = json.hasKey("fly_fx") ? json.getString("fly_fx") : null;
        this.hit_fx = json.hasKey("hit_fx") ? json.getString("hit_fx") : null;
        this.entity_model = json.hasKey("entity_model") ? json.getString("entity_model") : "arrow";
        this.shoot_mode = json.hasKey("shoot_mode") ? ShootMode.getByString(json.getString("shoot_mode")) : null;
        this.accuracy = json.hasKey("accuracy") ? json.getFloat("accuracy") : 1.0f;
        this.shootDelay = json.hasKey("shoot_delay") ? json.getInt("shoot_delay") : -1;
        this.knockback = json.hasKey("knockback") ? json.getFloat("knockback") : 0.0f;
        this.charge_time = json.hasKey("charge_time") ? json.getInt("charge_time") : -1;
        this.onAttack = json.hasKey("onAttack") ? new DamagePacket(json.getSubJson("onAttack")) : null;
        this.shoot_sound = json.hasKey("shoot_sound") ? json.getString("shoot_sound") : null;
        this.ice_damage = 0.0f;
        this.unbreakable = false;
        this.penetrate = false;
        this.noeff = false;
        this.maxRange = 30.0f;
        ItemLoader.ammoItem.put(json.getString("name"), (Object)this);
        AdventurerEngine.proxy.addSound(this.shoot_sound);
    }

    public BasicAmmo(by nbt) {
        super(nbt.l("theItem"));
        this.unbreakable = nbt.b("unbreakable") && nbt.d("unbreakable") == 1;
        this.entity_num = nbt.b("bullet_num") ? nbt.e("bullet_num") : 1;
        this.maxAmmoAmount = nbt.b("duration") ? nbt.e("duration") : 1;
        this.e(this.maxAmmoAmount);
        this.entity_model = nbt.b("entity_model") ? nbt.i("entity_model") : "arrow";
        this.fly_fx = nbt.b("fly_fx") ? nbt.i("fly_fx") : "";
        this.hit_fx = nbt.b("hit_fx") ? nbt.i("hit_fx") : "";
        RangeAttackPacket packet = RangeAttackPacket.getAttackPacket(nbt.e("fireArmAttr"));
        this.gravity = packet.gravity;
        this.penetrate = packet.penetrate;
        this.noeff = packet.noeff;
        this.shoot_mode = packet.shoot_mode;
        this.onAttack = packet.onAttack;
        this.shootDelay = packet.shoot_delay;
        this.accuracy = (float)packet.accuracy;
        this.basic_damage = (float)packet.basic_damage;
        this.ice_damage = (float)packet.ice_damage;
        this.knockback = (float)packet.knockback;
        this.maxRange = (float)packet.max_range;
        this.entity_speed = (float)packet.max_speed;
        this.shoot_sound = packet.shoot_sound;
        this.charge_time = packet.charge_time;
        ItemLoader.ammoItem.put(this.internal_name, (Object)this);
        AdventurerEngine.proxy.addSound(this.shoot_sound);
    }

    public static yc getAmmoByString(String name) {
        if ("arrow".equals(name)) {
            return yc.n;
        }
        return (yc)ItemLoader.ammoItem.get((Object)name);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        if (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54)) {
            if (this.basic_damage > 0.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.basic_damage"), Float.valueOf(this.basic_damage)));
            }
            if (this.ice_damage > 0.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.ice_damage"), Float.valueOf(this.ice_damage)));
            }
            if (this.knockback != -1.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.knockback"), Float.valueOf(this.knockback)));
            }
            if (this.entity_speed != -1.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.entity_speed"), Float.valueOf(this.entity_speed)));
            }
            list.add(String.format(bkb.a((String)"adv.attr.entity_num"), this.entity_num));
            if (this.maxRange != -1.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.maxRange"), Float.valueOf(this.maxRange)));
            }
            if (this.shoot_mode != null) {
                String mode = null;
                switch (this.shoot_mode) {
                    case SEMI: {
                        mode = bkb.a((String)"adv.attr.shoot_mode.semi");
                        break;
                    }
                    case AUTO: {
                        mode = bkb.a((String)"adv.attr.shoot_mode.auto");
                    }
                }
                list.add(String.format(bkb.a((String)"adv.attr.shoot_mode"), mode));
            }
            if (this.shootDelay != -1) {
                list.add(String.format(bkb.a((String)"adv.attr.shoot_delay"), this.shootDelay));
            }
            if (this.charge_time != -1) {
                list.add(String.format(bkb.a((String)"adv.attr.charge_time"), this.charge_time));
            }
            list.add(String.format(bkb.a((String)"adv.attr.accuracy"), Float.valueOf(this.accuracy)));
        } else {
            super.a(itemStack, player, list, par4);
            list.add("");
            list.add(bkb.a((String)"adv.moreinfo"));
        }
    }
}


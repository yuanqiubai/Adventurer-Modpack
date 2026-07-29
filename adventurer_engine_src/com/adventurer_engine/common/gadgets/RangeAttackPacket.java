/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.gadgets;

import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import java.util.HashMap;
import java.util.Map;

public class RangeAttackPacket {
    private static final Map<Integer, RangeAttackPacket> PACKETS = new HashMap<Integer, RangeAttackPacket>();
    public final boolean gravity;
    public final boolean penetrate;
    public final boolean noeff;
    public final ShootMode shoot_mode;
    public final DamagePacket onAttack;
    public final int shoot_delay;
    public final int charge_time;
    public final double accuracy;
    public final double basic_damage;
    public final double ice_damage;
    public final double knockback;
    public final double max_range;
    public final double max_speed;
    public final String shoot_sound;

    public RangeAttackPacket(by nbt) {
        this.gravity = nbt.b("gravity") && nbt.d("gravity") == 1;
        this.penetrate = nbt.b("penetrate") && nbt.d("penetrate") == 1;
        this.noeff = nbt.b("noeff") && nbt.d("noeff") == 1;
        this.shoot_mode = ShootMode.getByInt(nbt.d("shoot_mode"));
        this.onAttack = nbt.b("attack_packet") ? DamagePacket.getPacket(nbt.e("attack_packet")) : null;
        this.shoot_delay = nbt.e("shoot_delay");
        this.charge_time = nbt.e("charge_time");
        this.accuracy = nbt.h("accuracy");
        this.basic_damage = nbt.h("basic_damage");
        this.ice_damage = nbt.h("ice_damage");
        this.knockback = nbt.h("knockback");
        this.max_range = nbt.h("max_range");
        this.max_speed = nbt.h("max_speed");
        this.shoot_sound = nbt.i("shoot_sound");
        PACKETS.put(nbt.e("attr_id"), this);
    }

    public static RangeAttackPacket getAttackPacket(int id) {
        return PACKETS.get(id);
    }
}


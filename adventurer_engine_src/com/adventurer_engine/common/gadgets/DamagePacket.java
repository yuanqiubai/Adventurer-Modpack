/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cd
 *  cg
 *  nb
 *  nc
 *  nj
 *  nn
 *  of
 *  uf
 */
package com.adventurer_engine.common.gadgets;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.gadgets.AdditionBombs;
import com.adventurer_engine.util.JsonHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamagePacket {
    private static final Map<Integer, DamagePacket> PACKETS = new HashMap<Integer, DamagePacket>();
    private final float flu;
    private final float bomb_damage;
    private final int bomb_delay;
    private final String bomb_sound;
    private final boolean hasBomb;
    private final int bombAmount;
    private final nj[] effect_wounded;
    private final float[] p_effect_wounded;
    private final nj[] effect_attacker;
    private final float[] p_effect_attacker;

    public DamagePacket(JsonHelper json) {
        int i;
        List effects;
        float f = this.flu = json.hasKey("fluctuating") ? json.getFloat("fluctuating") : 0.0f;
        if (json.hasKey("bomb")) {
            JsonHelper bombJson = json.getSubJson("bomb");
            this.bomb_damage = bombJson.getFloat("damage");
            this.bomb_delay = bombJson.getInt("delay");
            this.bomb_sound = bombJson.hasKey("sound") ? bombJson.getString("sound") : null;
            AdventurerEngine.proxy.addSound(this.bomb_sound);
            this.hasBomb = true;
        } else {
            this.bomb_damage = 0.0f;
            this.bomb_delay = 0;
            this.bomb_sound = null;
            this.hasBomb = false;
        }
        int n = this.bombAmount = json.hasKey("bomb_amount") ? json.getInt("bomb_amount") : 0;
        if (json.hasKey("effect_wounded")) {
            effects = json.getList("effect_wounded");
            this.effect_wounded = DamagePacket.parseEffects(effects);
            if (json.hasKey("p_effect_wounded")) {
                effects = json.getList("p_effect_wounded");
                this.p_effect_wounded = new float[effects.size()];
                for (i = 0; i < effects.size(); ++i) {
                    this.p_effect_wounded[i] = ((Double)effects.get(i)).floatValue();
                }
            } else {
                this.p_effect_wounded = null;
            }
        } else {
            this.effect_wounded = null;
            this.p_effect_wounded = null;
        }
        if (json.hasKey("effect_attacker")) {
            effects = json.getList("effect_attacker");
            this.effect_attacker = DamagePacket.parseEffects(effects);
            if (json.hasKey("p_effect_attacker")) {
                effects = json.getList("p_effect_attacker");
                this.p_effect_attacker = new float[effects.size()];
                for (i = 0; i < effects.size(); ++i) {
                    this.p_effect_attacker[i] = ((Double)effects.get(i)).floatValue();
                }
            } else {
                this.p_effect_attacker = null;
            }
        } else {
            this.effect_attacker = null;
            this.p_effect_attacker = null;
        }
    }

    public DamagePacket(by nbt) {
        int i;
        cg ls2;
        this.flu = nbt.b("fluctuating") ? (float)nbt.h("fluctuating") : 0.0f;
        this.bomb_damage = nbt.b("bomb_damage") ? (float)nbt.h("bomb_damage") : 0.0f;
        this.bomb_delay = nbt.b("bomb_delay") ? nbt.e("bomb_delay") : 0;
        this.bomb_sound = nbt.b("bomb_sound") ? nbt.i("bomb_sound") : null;
        this.bombAmount = nbt.b("bomb_amount") ? nbt.e("bomb_amount") : 0;
        boolean bl = this.hasBomb = this.bomb_damage != 0.0f;
        if (nbt.b("effect_wounded")) {
            this.effect_wounded = DamagePacket.parseEffects(nbt.k("effect_wounded"));
            if (nbt.b("p_effect_wounded")) {
                ls2 = nbt.m("p_effect_wounded");
                this.p_effect_wounded = new float[ls2.c()];
                for (i = 0; i < this.p_effect_wounded.length; ++i) {
                    this.p_effect_wounded[i] = ((cd)ls2.b((int)i)).a;
                }
            } else {
                this.p_effect_wounded = null;
            }
        } else {
            this.effect_wounded = null;
            this.p_effect_wounded = null;
        }
        if (nbt.b("effect_attacker")) {
            this.effect_attacker = DamagePacket.parseEffects(nbt.k("effect_attacker"));
            if (nbt.b("p_effect_attacker")) {
                ls2 = nbt.m("p_effect_attacker");
                this.p_effect_attacker = new float[ls2.c()];
                for (i = 0; i < this.p_effect_attacker.length; ++i) {
                    this.p_effect_attacker[i] = ((cd)ls2.b((int)i)).a;
                }
            } else {
                this.p_effect_attacker = null;
            }
        } else {
            this.effect_attacker = null;
            this.p_effect_attacker = null;
        }
        PACKETS.put(nbt.e("packet_id"), this);
    }

    private static nj[] parseEffects(List<Double> arr) {
        nj[] effects = new nj[arr.size() / 3];
        for (int i = 0; i < arr.size(); i += 3) {
            effects[i / 3] = new nj(arr.get(i).intValue(), arr.get(i + 1).intValue(), arr.get(i + 2).intValue());
        }
        return effects;
    }

    private static nj[] parseEffects(int[] arr) {
        nj[] effects = new nj[arr.length / 3];
        for (int i = 0; i < arr.length; i += 3) {
            effects[i / 3] = new nj(arr[i], arr[i + 1], arr[i + 2]);
        }
        return effects;
    }

    private AdditionBombs getBomb(uf player, int stage) {
        return new AdditionBombs().setDamage(this.bomb_damage).setDelay(this.bomb_delay * stage).setSound("adventurer_engine:" + this.bomb_sound).setAttacker((of)player);
    }

    public boolean causeDamage(uf attacker, of wounded, float basic_damage, float ice_damage, nb dmg) {
        float f = basic_damage = this.flu == 0.0f ? basic_damage : PlayerTools.randomGaussian(basic_damage, this.flu);
        if (this.hasBomb) {
            for (int i = 0; i < this.bombAmount; ++i) {
                AdditionBombs.addBomb(wounded, this.getBomb(attacker, i + 1));
            }
        }
        this.buffGiver(wounded, this.effect_wounded, this.p_effect_wounded);
        this.buffGiver((of)attacker, this.effect_attacker, this.p_effect_attacker);
        boolean toRet = true;
        if (basic_damage > 0.0f) {
            toRet = wounded.a(dmg, basic_damage);
            wounded.af = 0;
        }
        if (ice_damage > 0.0f) {
            toRet |= wounded.a((nb)new nc("ice_damage", (nn)attacker), ice_damage);
            wounded.af = 0;
        }
        return toRet;
    }

    public boolean causeDamage(uf attacker, of wounded, float[] damages, float[] defenses) {
        int i;
        int len = Math.min(damages.length, defenses.length);
        for (i = 0; i < len; ++i) {
            damages[i] = this.flu == 0.0f ? damages[i] : PlayerTools.randomGaussian(damages[i], this.flu);
            int n = i;
            damages[n] = damages[n] * defenses[i];
        }
        if (this.hasBomb) {
            for (i = 0; i < this.bombAmount; ++i) {
                AdditionBombs.addBomb(wounded, this.getBomb(attacker, i));
            }
        }
        if (this.effect_wounded != null) {
            this.buffGiver(wounded, this.effect_wounded, this.p_effect_wounded);
        }
        if (this.effect_attacker != null) {
            this.buffGiver((of)attacker, this.effect_attacker, this.p_effect_attacker);
        }
        boolean flag = false;
        for (int i2 = 0; i2 < len; ++i2) {
            flag = wounded.a(nb.a((uf)attacker), damages[i2]) || flag;
        }
        return flag;
    }

    private void buffGiver(of target, nj[] effects, float[] percent) {
        if (effects == null) {
            return;
        }
        if (percent == null) {
            for (nj effect : effects) {
                target.c(new nj(effect));
            }
        } else {
            if (percent.length < effects.length) {
                return;
            }
            for (int i = 0; i < effects.length; ++i) {
                if (!PlayerTools.percent(percent[i])) continue;
                target.c(new nj(effects[i]));
            }
        }
    }

    public static DamagePacket getPacket(int id) {
        return PACKETS.get(id);
    }
}


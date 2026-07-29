/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bkb
 *  by
 *  cg
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 *  ms
 *  mt
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  net.minecraftforge.event.entity.player.ArrowNockEvent
 *  nn
 *  of
 *  org.lwjgl.input.Keyboard
 *  uf
 *  yc
 *  ye
 *  zj
 */
package com.adventurer_engine.common.items.blank.ranged_weapon;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelArrow;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelBullet;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.gadgets.RangeAttackPacket;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.network.server2client.PlaySoundPacket;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.JsonHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.LinkedList;
import java.util.List;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import org.lwjgl.input.Keyboard;

public class BasicRangedWeapon
extends BasicItem {
    public static final String CURRENT_AMMO = "current_ammo";
    public final boolean unbreakable;
    public final int charge_stage;
    public final List<String> allowed_ammo;
    public final int shoot_number;
    public final boolean gravity;
    public final boolean penetrate;
    public final boolean noeff;
    public final float accuracy;
    public final DamagePacket onAttack;
    public final String shoot_sound;
    public final int charge_time;
    public final int shoot_delay;
    public final float basic_damage;
    public final float ice_damage;
    public final float knockback;
    public final ShootMode shoot_mode;
    public final List<String> icons;
    public final float maxRange;
    public final float maxSpeed;
    @SideOnly(value=Side.CLIENT)
    private ms[] chargeIcon;
    private static final int SEMI_DELAY = 6;

    public BasicRangedWeapon(int id, JsonHelper json, String tab) {
        super(id, json, tab);
        this.e(json.getInt("duration"));
        this.charge_stage = json.hasKey("charge_stage") ? json.getInt("charge_stage") : 0;
        this.charge_time = json.hasKey("charge_time") ? json.getInt("charge_time") : 40;
        this.basic_damage = json.hasKey("basic_damage") ? json.getFloat("basic_damage") : 4.0f;
        this.knockback = json.hasKey("knockback") ? json.getFloat("knockback") : 0.0f;
        this.icons = json.hasKey("icons") ? json.getList("icons") : null;
        this.shoot_mode = json.hasKey("shoot_mode") ? ShootMode.getByString(json.getString("shoot_mode")) : ShootMode.SEMI;
        this.allowed_ammo = json.hasKey("allowed_ammo") ? json.getList("allowed_ammo") : null;
        this.shoot_number = json.hasKey("shoot_number") ? json.getInt("shoot_number") : 1;
        this.unbreakable = json.hasKey("unbreakable") && json.getBoolean("unbreakable");
        this.shoot_delay = json.hasKey("shoot_delay") ? json.getInt("shoot_delay") : 0;
        this.accuracy = json.hasKey("accuracy") ? json.getFloat("accuracy") : 1.0f;
        this.shoot_sound = json.hasKey("shoot_sound") ? json.getString("shoot_sound") : "bow";
        this.onAttack = json.hasKey("onAttack") ? new DamagePacket(json.getSubJson("onAttack")) : null;
        this.cw = 1;
        this.ice_damage = 0.0f;
        this.gravity = true;
        this.penetrate = false;
        this.noeff = false;
        this.maxRange = 30.0f;
        this.maxSpeed = 20.0f;
        AdventurerEngine.proxy.addSound(this.shoot_sound);
    }

    public BasicRangedWeapon(by nbt) {
        super(nbt.l("theItem"));
        int i;
        cg ls2;
        DebugGadgets.debugInfo("loading guns");
        this.e(nbt.e("duration"));
        this.unbreakable = nbt.b("unbreakable") && nbt.d("unbreakable") == 1;
        this.charge_stage = nbt.d("charge_stage");
        this.shoot_number = nbt.b("shoot_number") ? nbt.e("shoot_number") : 0;
        this.cw = 1;
        DebugGadgets.debugInfo("allowed_ammo!");
        if (nbt.b("allowed_ammo")) {
            DebugGadgets.debugInfo("hasKey(allowed_ammo)");
            ls2 = nbt.m("allowed_ammo");
            this.allowed_ammo = new LinkedList<String>();
            for (i = 0; i < ls2.c(); ++i) {
                this.allowed_ammo.add(ls2.b(i).toString());
            }
        } else {
            this.allowed_ammo = null;
        }
        if (nbt.b("charge_icons")) {
            ls2 = nbt.m("charge_icons");
            this.icons = new LinkedList<String>();
            for (i = 0; i < ls2.c(); ++i) {
                this.icons.add(ls2.b(i).toString());
            }
        } else {
            this.icons = null;
        }
        RangeAttackPacket packet = RangeAttackPacket.getAttackPacket(nbt.e("fireArmAttr"));
        this.gravity = packet.gravity;
        this.penetrate = packet.penetrate;
        this.noeff = packet.noeff;
        this.shoot_mode = packet.shoot_mode;
        this.onAttack = packet.onAttack;
        this.shoot_delay = packet.shoot_delay;
        this.accuracy = (float)packet.accuracy;
        this.basic_damage = (float)packet.basic_damage;
        this.ice_damage = (float)packet.ice_damage;
        this.knockback = (float)packet.knockback;
        this.maxRange = (float)packet.max_range;
        this.maxSpeed = (float)packet.max_speed;
        this.shoot_sound = packet.shoot_sound;
        this.charge_time = packet.charge_time;
        AdventurerEngine.proxy.addSound(this.shoot_sound);
    }

    public void a(ye item, abw world, uf player, int uc2) {
        if (this.getCharge_stage(item) < 1) {
            return;
        }
        player.shoot_delay = this.getShootDelay(item, player);
        this.handleShoot(player, item, item.q().i(CURRENT_AMMO));
    }

    public ye b(ye item, abw world, uf player) {
        if (this.getShoot_mode(item) == ShootMode.SEMI) {
            return item;
        }
        player.shoot_delay = this.getShootDelay(item, player);
        this.handleShoot(player, item, item.q().i(CURRENT_AMMO));
        return item;
    }

    public ye a(ye item, abw world, uf player) {
        ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return event.result;
        }
        int ct = this.getCharge_time(item);
        ShootMode mode = this.getShoot_mode(item);
        if (item.p() && item.q().b(CURRENT_AMMO) && BasicRangedWeapon.hasAmmo(player, item.q().i(CURRENT_AMMO))) {
            if (ct == 0) {
                if (!world.I) {
                    if (player.shoot_delay > 1) {
                        if (mode == ShootMode.SEMI && player.shoot_delay <= 6) {
                            player.shoot_delay = 6;
                        }
                        return item;
                    }
                    if (player.shoot_delay == 0) {
                        this.handleShoot(player, item, item.q().i(CURRENT_AMMO));
                        player.shoot_delay = this.getShootDelay(item, player);
                        return item;
                    }
                }
            } else {
                if (player.shoot_delay > 1) {
                    return item;
                }
                player.a(item, this.d_(item));
            }
            return item;
        }
        String newAmmo = this.hasAllowedAmmo(player);
        if (newAmmo != null) {
            BasicRangedWeapon.updateCurrentAmmo(item, newAmmo);
            if (this.getCharge_time(item) == 0) {
                return this.handleShoot(player, item, newAmmo);
            }
            player.a(item, this.d_(item));
            return item;
        }
        return item;
    }

    public int d_(ye item) {
        return this.getShoot_mode(item) == ShootMode.AUTO ? this.getCharge_time(item) : 72000;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void a(mt reg) {
        super.a(reg);
        if (this.charge_stage < 1) {
            return;
        }
        this.chargeIcon = new ms[this.charge_stage];
        for (int i = 0; i < this.charge_stage; ++i) {
            this.chargeIcon[i] = i < this.icons.size() ? reg.a("adventurer_engine:" + this.icons.get(i)) : this.chargeIcon[i - 1];
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.getIcon(item);
    }

    @SideOnly(value=Side.CLIENT)
    public ms getIcon(ye item, int renderPass, uf player, ye usingItem, int useRemaining) {
        return this.getIcon(item);
    }

    @SideOnly(value=Side.CLIENT)
    public boolean n_() {
        return true;
    }

    public zj c_(ye item) {
        return zj.e;
    }

    private ms getIcon(ye item) {
        int dur = item.use_duration;
        if (dur <= 0) {
            return this.cz;
        }
        int stage = Math.min((int)Math.floor((double)dur / (double)this.getCharge_time(item) * (double)this.charge_stage), this.chargeIcon.length) - 1;
        stage = Math.max(0, stage);
        return this.chargeIcon[stage];
    }

    private ye handleShoot(uf player, ye item, String ammoName) {
        ye result = item;
        for (int i = 0; i < this.shoot_number; ++i) {
            result = this.shootOnce(player, item, ammoName);
        }
        return result;
    }

    private ye shootOnce(uf player, ye item, String ammoName) {
        int entityNum;
        if (!BasicRangedWeapon.hasAmmo(player, ammoName)) {
            return item;
        }
        if (!this.unbreakable) {
            item.a(1, (of)player);
        }
        int chargeStage = Math.min((int)Math.floor((double)item.use_duration / (double)this.getCharge_time(item) * (double)this.charge_stage), this.charge_stage);
        float chargeRatio = (float)chargeStage / (float)this.charge_stage;
        if (this.getCharge_time(item) <= 1) {
            chargeRatio = 1.0f;
        }
        float basicDamage = this.getBasic_damage(item) * chargeRatio;
        float iceDamage = this.getIce_damage(item) * chargeRatio;
        float knockBack = this.getKnockback(item) * chargeRatio;
        float accuracies = this.getAccuracy(item);
        float inUseMaxRange = this.getMaxRange(item);
        boolean gravity = this.gravity;
        boolean penetrate = this.penetrate;
        boolean noeff = this.noeff;
        String entityModel = "arrow";
        String sound = this.shoot_sound;
        DamagePacket pkg = null;
        float entitySpeed = this.getMaxSpeed(item);
        if ("arrow".equals(ammoName)) {
            entityNum = 1;
        } else {
            BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
            entityNum = ammo.entity_num;
            entitySpeed = ammo.entity_speed;
            entityModel = ammo.entity_model;
            sound = ammo.shoot_sound != null ? ammo.shoot_sound : sound;
            gravity = ammo.gravity;
            penetrate = penetrate || ammo.penetrate;
            noeff = noeff || ammo.noeff;
            pkg = ammo.onAttack;
        }
        pkg = pkg == null ? this.onAttack : pkg;
        entitySpeed *= chargeRatio;
        PlaySoundPacket soundPkg = new PlaySoundPacket(sound, 1.0f, 1.0f / (f.nextFloat() * 0.4f + 1.2f), (nn)player);
        for (int i = 0; i < entityNum; ++i) {
            EntityBulletGeneral arr;
            if ("bullet".equals(entityModel)) {
                arr = new EntityBulletModelBullet(player.q, (of)player, entitySpeed, accuracies);
            } else if (entityModel.startsWith("item_")) {
                int id = Integer.parseInt(entityModel.substring(5));
                arr = new EntityBulletModelItem(player.q, (of)player, entitySpeed, accuracies);
                ((EntityBulletModelItem)arr).setRenderItemID(id);
            } else {
                arr = new EntityBulletModelArrow(player.q, (of)player, entitySpeed, accuracies);
            }
            arr.setDamage(basicDamage);
            arr.setKnockbackStrength(knockBack);
            arr.setDamagePacket(pkg);
            arr.setGravity(gravity);
            arr.setMaxRange(inUseMaxRange);
            arr.setNoeff(noeff);
            arr.setPenetrate(penetrate);
            arr.setIceDamage(iceDamage);
            if (player.q.I) continue;
            player.q.d((nn)arr);
            PacketDispatcher.sendPacketToPlayer((ey)soundPkg.makePacket(), (Player)((Player)player));
        }
        PlayerTools.consumeAmmo(player, ammoName);
        return item;
    }

    private int getShootDelay(ye item, uf player) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.shoot_delay;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.shoot_delay;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.shootDelay == -1 ? this.shoot_delay : ammo.shootDelay;
    }

    private float getBasic_damage(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.basic_damage;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.basic_damage;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.basic_damage + this.basic_damage;
    }

    private float getIce_damage(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.ice_damage;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.ice_damage;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.ice_damage + this.ice_damage;
    }

    private float getKnockback(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.knockback;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.knockback;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.knockback == -1.0f ? this.knockback : ammo.knockback;
    }

    private float getAccuracy(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.accuracy;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.accuracy;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.accuracy == -1.0f ? this.accuracy : ammo.accuracy;
    }

    private float getMaxRange(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.maxRange;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.maxRange;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.maxRange == -1.0f ? this.maxRange : ammo.maxRange;
    }

    private int getCharge_time(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.charge_time;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.charge_time;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.charge_time == -1 ? this.charge_time : ammo.charge_time;
    }

    private ShootMode getShoot_mode(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.shoot_mode;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.shoot_mode;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.shoot_mode == null ? this.shoot_mode : ammo.shoot_mode;
    }

    private float getMaxSpeed(ye item) {
        if (!item.p() || !item.q().b(CURRENT_AMMO)) {
            return this.maxSpeed;
        }
        String ammoName = item.q().i(CURRENT_AMMO);
        if ("arrow".equals(ammoName)) {
            return this.maxSpeed;
        }
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        return ammo.entity_speed == -1.0f ? this.maxSpeed : ammo.entity_speed;
    }

    private static boolean hasAmmo(uf player, String ammoName) {
        yc ammo = BasicAmmo.getAmmoByString(ammoName);
        return PlayerTools.hasItem(player, ammo);
    }

    private String hasAllowedAmmo(uf player) {
        for (String ammoName : this.allowed_ammo) {
            if (!BasicRangedWeapon.hasAmmo(player, ammoName)) continue;
            return ammoName;
        }
        return null;
    }

    public static void updateCurrentAmmo(ye item, String ammoName) {
        if (item.p()) {
            item.q().a(CURRENT_AMMO, ammoName);
        } else {
            by nbt = new by();
            nbt.a(CURRENT_AMMO, ammoName);
            item.d(nbt);
        }
    }

    private int getCharge_stage(ye item) {
        int all_charge_time = this.getCharge_time(item);
        int charge_time = item.use_duration;
        return (int)Math.floor((double)charge_time / (double)all_charge_time * (double)this.charge_stage);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        if (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54)) {
            list.add(String.format(bkb.a((String)"adv.attr.basic_damage"), Float.valueOf(this.basic_damage)));
            if (this.ice_damage > 0.0f) {
                list.add(String.format(bkb.a((String)"adv.attr.ice_damage"), Float.valueOf(this.ice_damage)));
            }
            list.add(String.format(bkb.a((String)"adv.attr.knockback"), Float.valueOf(this.knockback)));
            list.add(String.format(bkb.a((String)"adv.attr.shoot_number"), this.shoot_number));
            list.add(String.format(bkb.a((String)"adv.attr.maxRange"), Float.valueOf(this.maxRange)));
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
            if (this.shoot_delay > 0) {
                list.add(String.format(bkb.a((String)"adv.attr.shoot_delay"), this.shoot_delay));
            }
            if (this.charge_time >= 0) {
                list.add(String.format(bkb.a((String)"adv.attr.charge_time"), this.charge_time));
            }
            if (this.charge_stage > 0) {
                list.add(String.format(bkb.a((String)"adv.attr.charge_stage"), this.charge_stage));
            }
            list.add(String.format(bkb.a((String)"adv.attr.accuracy"), Float.valueOf(this.accuracy)));
            if (itemStack.p() && itemStack.q().b(CURRENT_AMMO)) {
                list.add(String.format(bkb.a((String)"adv.attr.current_ammo"), bkb.a((String)("item." + itemStack.q().i(CURRENT_AMMO) + ".name"))));
            }
            list.add(bkb.a((String)"adv.attr.allowed_ammo"));
            for (String ammoName : this.allowed_ammo) {
                list.add("  -" + bkb.a((String)("item." + ammoName + ".name")));
            }
        } else {
            super.a(itemStack, player, list, par4);
            if (this.unbreakable) {
                list.add(bkb.a((String)"adv.unbreakable"));
            }
            list.add("");
            list.add(bkb.a((String)"adv.moreinfo"));
        }
    }
}


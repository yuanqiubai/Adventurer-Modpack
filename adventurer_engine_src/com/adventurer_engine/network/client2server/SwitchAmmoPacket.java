/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.network.client2server;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class SwitchAmmoPacket
extends RCsPacket {
    private boolean isUp;

    public SwitchAmmoPacket() {
    }

    public SwitchAmmoPacket(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeBoolean(this.isUp);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.isUp = in.readBoolean();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        int size;
        yc currentAmmo;
        if (side.isClient()) {
            return;
        }
        ye item = player.aZ();
        if (item == null) {
            return;
        }
        if (!(item.b() instanceof BasicRangedWeapon)) {
            return;
        }
        int ammoListStart = 0;
        String currentAmmoName = null;
        BasicRangedWeapon weapon = (BasicRangedWeapon)item.b();
        if (item.p() && item.q().b("current_ammo") && PlayerTools.hasItem(player, currentAmmo = BasicAmmo.getAmmoByString(currentAmmoName = item.q().i("current_ammo")))) {
            for (int i = 0; i < weapon.allowed_ammo.size(); ++i) {
                if ((currentAmmo.cv != yc.n.cv || !"arrow".equals(weapon.allowed_ammo.get(i))) && !((BasicAmmo)currentAmmo).internal_name.equals(weapon.allowed_ammo.get(i))) continue;
                ammoListStart = i;
                break;
            }
        }
        String nextAmmoName = null;
        if (this.isUp) {
            size = weapon.allowed_ammo.size();
            for (int i = size - 1; i >= 0; --i) {
                int c = (i + ammoListStart + size) % size;
                String name = weapon.allowed_ammo.get(c);
                if (name.equals(currentAmmoName) || !PlayerTools.hasItem(player, BasicAmmo.getAmmoByString(name))) continue;
                nextAmmoName = weapon.allowed_ammo.get(c);
                break;
            }
        } else {
            size = weapon.allowed_ammo.size();
            for (int i = 0; i < size; ++i) {
                int c = (i + ammoListStart + size) % size;
                String name = weapon.allowed_ammo.get(c);
                if (name.equals(currentAmmoName) || !PlayerTools.hasItem(player, BasicAmmo.getAmmoByString(name))) continue;
                nextAmmoName = weapon.allowed_ammo.get(c);
                break;
            }
        }
        if (nextAmmoName != null && !nextAmmoName.equals(currentAmmoName)) {
            if (!item.p()) {
                item.d(new by());
            }
            item.q().a("current_ammo", nextAmmoName);
            player.q.a((nn)player, "adventurer_engine:adventurer_engine:switch_ammo", 1.0f, 1.0f);
        }
    }
}


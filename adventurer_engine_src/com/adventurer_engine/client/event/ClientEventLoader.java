/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ats
 *  atv
 *  bdi
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeSubscribe
 */
package com.adventurer_engine.client.event;

import com.adventurer_engine.client.key.KeyInputEvent;
import com.adventurer_engine.client.key.KeyTickHandler;
import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.network.client2server.RenewDurationDelayPacket;
import com.adventurer_engine.network.client2server.RequestSlideStep;
import com.adventurer_engine.network.client2server.SwitchAmmoPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(value=Side.CLIENT)
public class ClientEventLoader {
    private long lastTime = 0L;
    private static final RenewDurationDelayPacket renewDurationPacket = new RenewDurationDelayPacket();
    private static final SwitchAmmoPacket switch_up = new SwitchAmmoPacket(true);
    private static final SwitchAmmoPacket switch_down = new SwitchAmmoPacket(false);

    public ClientEventLoader() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @ForgeSubscribe
    public void actionSlideStep(KeyInputEvent event) {
        atv mc = atv.w();
        if (!mc.A) {
            return;
        }
        ats currentKey = event.key;
        if (currentKey.equals(KeyTickHandler.keys[0])) {
            this.handleSlideStep(event);
        } else if (currentKey.equals(KeyTickHandler.keys[1])) {
            this.handleAmmoSwitch(event, true);
        } else if (currentKey.equals(KeyTickHandler.keys[2])) {
            this.handleAmmoSwitch(event, false);
        } else if (currentKey.equals(atv.w().u.R) || currentKey.equals(atv.w().u.S)) {
            PacketDispatcher.sendPacketToServer((ey)renewDurationPacket.makePacket());
        }
    }

    private void handleAmmoSwitch(KeyInputEvent event, boolean isUp) {
        if (event.keyUp()) {
            return;
        }
        if (isUp) {
            PacketDispatcher.sendPacketToServer((ey)switch_up.makePacket());
        } else {
            PacketDispatcher.sendPacketToServer((ey)switch_down.makePacket());
        }
    }

    private void handleSlideStep(KeyInputEvent event) {
        int deley;
        if (!CommonConfig.enableSlideStep) {
            return;
        }
        atv mc = atv.w();
        bdi player = mc.h;
        if (player.bI().a() == 0) {
            return;
        }
        if (event.keyUp()) {
            return;
        }
        long timeNow = System.currentTimeMillis();
        if (timeNow - this.lastTime >= (long)(deley = 500)) {
            double velocity = 1.2;
            if (!player.F) {
                velocity /= 2.5;
            }
            double theta = player.z == 0.0 && player.x == 0.0 ? (double)(-player.ap()) * Math.PI / 180.0 : (player.z == 0.0 ? (player.x > 0.0 ? 2.356194490192345 : 1.5707963267948966) : (player.x == 0.0 ? (player.z > 0.0 ? 0.0 : Math.PI) : 1.5707963267948966 - Math.atan2(player.z, -player.x)));
            double x = (float)(-velocity * Math.sin(theta));
            double z = (float)(velocity * Math.cos(theta));
            player.g(x, 0.0, z);
            PacketDispatcher.sendPacketToServer((ey)new RequestSlideStep().makePacket());
            this.lastTime = timeNow;
        }
    }
}


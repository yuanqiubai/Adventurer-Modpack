/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package com.adventurer_engine.common.entity.neo_npc;

import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.network.server2client.NPCAnimationPacket;
import com.adventurer_engine.util.blockbench.BBModel;
import cpw.mods.fml.common.network.PacketDispatcher;

public class AnimationController {
    public boolean launchAnimation(String name, EntityRCsNPCBase npc) {
        BBModel model = npc.model;
        if (model.animations.containsKey((Object)name)) {
            ey pkt = new NPCAnimationPacket(name, npc.k).makePacket();
            PacketDispatcher.sendPacketToAllPlayers((ey)pkt);
            return true;
        }
        return false;
    }

    public void onUpdate() {
    }
}


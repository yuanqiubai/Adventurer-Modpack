/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ad
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  jv
 *  z
 */
package com.adventurer_engine.common.command;

import com.adventurer_engine.network.server2client.PlayTitlePacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class CommandTitle
extends z {
    public String c() {
        return "title";
    }

    public String c(ad icommandsender) {
        return "commands.title.usage";
    }

    public void b(ad sender, String[] args) {
        if (args.length < 2) {
            return;
        }
        jv player = CommandTitle.d((ad)sender, (String)args[0]);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayTitlePacket(args[1]).makePacket(), (Player)((Player)player));
    }

    public int a() {
        return 2;
    }
}


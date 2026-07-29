/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adr
 *  atv
 *  bdd
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  nn
 *  noppes.common.entity.EntityNPCInterface
 */
package com.adventurer_engine.client.event;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import java.util.List;
import noppes.common.entity.EntityNPCInterface;

public class RCsTickHandlerClient
implements ITickHandler {
    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
    }

    public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
        bdd world = atv.w().f;
        if (world == null) {
            return;
        }
        for (nn e : world.e) {
            if (!(e instanceof EntityNPCInterface)) continue;
            adr chunk = e.q.e(e.aj, e.al);
            boolean find = false;
            for (List ls : chunk.j) {
                for (nn entity : ls) {
                    if (entity != e) continue;
                    find = true;
                    break;
                }
                if (find) break;
            }
            if (find) continue;
            chunk.a(e);
        }
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    public String getLabel() {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  uf
 */
package noppes.common.roles;

import java.util.HashMap;
import noppes.common.controllers.InnDoorData;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;

public class RoleInnkeeper
extends RoleInterface {
    private String innName = "Inn";
    private HashMap<String, InnDoorData> doors = new HashMap();

    public RoleInnkeeper(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("InnName", this.innName);
        nbttagcompound.a("InnDoors", this.nbtInnDoors(this.doors));
    }

    private cl nbtInnDoors(HashMap<String, InnDoorData> doors1) {
        cg nbttaglist = new cg();
        if (doors1 == null) {
            return nbttaglist;
        }
        HashMap<String, InnDoorData> doors2 = doors1;
        for (String name : doors1.keySet()) {
            InnDoorData door = doors2.get(name);
            if (door == null) continue;
            by nbttagcompound = new by();
            nbttagcompound.a("Name", name);
            nbttagcompound.a("posX", door.x);
            nbttagcompound.a("posY", door.y);
            nbttagcompound.a("posZ", door.z);
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.innName = nbttagcompound.i("InnName");
        this.doors = this.getInnDoors(nbttagcompound.m("InnDoors"));
    }

    private HashMap<String, InnDoorData> getInnDoors(cg tagList) {
        HashMap<String, InnDoorData> list = new HashMap<String, InnDoorData>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            String name = nbttagcompound.i("Name");
            InnDoorData door = new InnDoorData();
            door.x = nbttagcompound.e("posX");
            door.y = nbttagcompound.e("posY");
            door.z = nbttagcompound.e("posZ");
            list.put(name, door);
        }
        return list;
    }

    @Override
    public boolean interact(uf player) {
        this.npc.say(player, this.npc.advanced.getInteractLine());
        if (this.doors.isEmpty()) {
            player.a("No Rooms available");
            return true;
        }
        return false;
    }
}


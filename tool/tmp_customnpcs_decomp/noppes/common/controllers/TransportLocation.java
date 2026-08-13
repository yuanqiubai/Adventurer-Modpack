/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.controllers;

import noppes.common.controllers.TransportCategory;
import noppes.common.entity.EntityNPCInterface;

public class TransportLocation {
    public int id = -1;
    public String name = "default name";
    public double posX;
    public double posY;
    public double posZ;
    public int npcX;
    public int npcY;
    public int npcZ;
    public int type = 0;
    public int dimension = 0;
    public TransportCategory category;

    public void readNBT(by compound) {
        if (compound != null) {
            this.id = compound.e("Id");
            this.posX = compound.h("PosX");
            this.posY = compound.h("PosY");
            this.posZ = compound.h("PosZ");
            this.npcX = compound.e("PosNpcX");
            this.npcY = compound.e("PosNpcY");
            this.npcZ = compound.e("PosNpcZ");
            this.type = compound.e("Type");
            this.dimension = compound.e("Dimension");
            this.name = compound.i("Name");
        }
    }

    public by writeNBT() {
        by compound = new by();
        compound.a("Id", this.id);
        compound.a("PosX", this.posX);
        compound.a("PosY", this.posY);
        compound.a("PosZ", this.posZ);
        compound.a("PosNpcX", this.npcX);
        compound.a("PosNpcY", this.npcY);
        compound.a("PosNpcZ", this.npcZ);
        compound.a("Type", this.type);
        compound.a("Dimension", this.dimension);
        compound.a("Name", this.name);
        return compound;
    }

    public boolean isNpc(EntityNPCInterface npc) {
        return npc.startPos[0] == this.npcX && npc.startPos[1] == this.npcY && npc.startPos[2] == this.npcZ;
    }

    public boolean isDefault() {
        return this.type == 1;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 *  uf
 */
package noppes.common.controllers;

import java.util.HashSet;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerFactionData;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NBTTags;

public class Faction {
    public String name = "";
    public int color = Integer.parseInt("FF00", 16);
    public HashSet<Integer> attackFactions;
    public int id = -1;
    public int neutralPoints = 500;
    public int friendlyPoints = 1500;
    public int minPoints = 0;
    public int maxPoints = 2000;
    public int defaultPoints = 1000;
    public boolean hideFaction = false;
    public boolean getsAttacked = false;

    public Faction() {
        this.attackFactions = new HashSet();
    }

    public Faction(int id, String name, int color, int defaultPoints) {
        this.name = name;
        this.color = color;
        this.defaultPoints = defaultPoints;
        this.id = id;
        this.attackFactions = new HashSet();
    }

    public static String formatName(String name) {
        name = name.toLowerCase().trim();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void readNBT(by compound) {
        this.name = compound.i("Name");
        this.color = compound.e("Color");
        this.id = compound.e("Slot");
        this.neutralPoints = compound.e("NeutralPoints");
        this.friendlyPoints = compound.e("FriendlyPoints");
        this.defaultPoints = compound.e("DefaultPoints");
        this.hideFaction = compound.n("HideFaction");
        this.getsAttacked = compound.n("GetsAttacked");
        this.attackFactions = NBTTags.getIntegerSet(compound.m("AttackFactions"));
    }

    public void writeNBT(by compound) {
        compound.a("Slot", this.id);
        compound.a("Name", this.name);
        compound.a("Color", this.color);
        compound.a("NeutralPoints", this.neutralPoints);
        compound.a("FriendlyPoints", this.friendlyPoints);
        compound.a("DefaultPoints", this.defaultPoints);
        compound.a("HideFaction", this.hideFaction);
        compound.a("GetsAttacked", this.getsAttacked);
        compound.a("AttackFactions", (cl)NBTTags.nbtIntegerSet(this.attackFactions));
    }

    public boolean isFriendlyToPlayer(uf player) {
        PlayerFactionData data = PlayerDataController.instance.getPlayerData((uf)player).factionData;
        return data.getFactionPoints(this.id) >= this.friendlyPoints;
    }

    public boolean isAggressiveToPlayer(uf player) {
        PlayerFactionData data = PlayerDataController.instance.getPlayerData((uf)player).factionData;
        return data.getFactionPoints(this.id) < this.neutralPoints;
    }

    public boolean isAggressiveToNpc(EntityNPCInterface entity) {
        return this.attackFactions.contains(entity.getFaction().id);
    }
}


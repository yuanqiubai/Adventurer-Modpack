/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cv
 *  uf
 */
package noppes.common.controllers;

import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerFactionData;

public class FactionOptions {
    public boolean decreaseFactionPoints = false;
    public boolean decreaseFaction2Points = false;
    public int factionId = -1;
    public int faction2Id = -1;
    public int factionPoints = 100;
    public int faction2Points = 100;

    public void readFromNBT(by compound) {
        this.factionId = compound.e("OptionFactions1");
        this.faction2Id = compound.e("OptionFactions2");
        this.decreaseFactionPoints = compound.n("DecreaseFaction1Points");
        this.decreaseFaction2Points = compound.n("DecreaseFaction2Points");
        this.factionPoints = compound.e("OptionFaction1Points");
        this.faction2Points = compound.e("OptionFaction2Points");
    }

    public by writeToNBT(by par1NBTTagCompound) {
        par1NBTTagCompound.a("OptionFactions1", this.factionId);
        par1NBTTagCompound.a("OptionFactions2", this.faction2Id);
        par1NBTTagCompound.a("OptionFaction1Points", this.factionPoints);
        par1NBTTagCompound.a("OptionFaction2Points", this.faction2Points);
        par1NBTTagCompound.a("DecreaseFaction1Points", this.decreaseFactionPoints);
        par1NBTTagCompound.a("DecreaseFaction2Points", this.decreaseFaction2Points);
        return par1NBTTagCompound;
    }

    public boolean hasFaction(int id) {
        return this.factionId == id || this.faction2Id == id;
    }

    public void addPoints(uf player) {
        if (this.factionId >= 0 || this.faction2Id >= 0) {
            PlayerFactionData data = PlayerDataController.instance.getPlayerData((uf)player).factionData;
            if (this.factionId >= 0 && this.factionPoints > 0) {
                this.addPoints(player, data, this.factionId, this.decreaseFactionPoints, this.factionPoints);
            }
            if (this.faction2Id >= 0 && this.faction2Points > 0) {
                this.addPoints(player, data, this.faction2Id, this.decreaseFaction2Points, this.faction2Points);
            }
        }
    }

    private void addPoints(uf player, PlayerFactionData data, int factionId, boolean decrease, int points) {
        Faction faction = FactionController.getInstance().getFaction(factionId);
        if (faction != null) {
            if (!faction.hideFaction) {
                String message = decrease ? "faction.decreasepoints" : "faction.increasepoints";
                player.a(cv.b((String)message, (Object[])new Object[]{faction.name, points}));
            }
            data.increasePoints(factionId, decrease ? -points : points);
        }
    }
}


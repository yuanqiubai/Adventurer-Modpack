/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  net.minecraftforge.common.IExtendedEntityProperties
 *  nn
 */
package noppes.common.controllers;

import net.minecraftforge.common.IExtendedEntityProperties;
import noppes.common.controllers.PlayerBankData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerDialogData;
import noppes.common.controllers.PlayerFactionData;
import noppes.common.controllers.PlayerItemGiverData;
import noppes.common.controllers.PlayerMailData;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.PlayerTransportData;

public class PlayerData
implements IExtendedEntityProperties {
    public PlayerDialogData dialogData = new PlayerDialogData();
    public PlayerBankData bankData = new PlayerBankData();
    public PlayerQuestData questData = new PlayerQuestData();
    public PlayerTransportData transportData = new PlayerTransportData();
    public PlayerFactionData factionData = new PlayerFactionData();
    public PlayerItemGiverData itemgiverData = new PlayerItemGiverData();
    public PlayerMailData mailData = new PlayerMailData();
    private final String playerName;

    public PlayerData(String player) {
        this.playerName = player;
        this.readNBT(PlayerDataController.instance.loadPlayerData(this.playerName));
    }

    public void readNBT(by compound) {
        this.dialogData.readNBT(compound);
        this.bankData.readNBT(compound);
        this.questData.readNBT(compound);
        this.transportData.readNBT(compound);
        this.factionData.readNBT(compound);
        this.itemgiverData.readNBT(compound);
        this.mailData.readNBT(compound);
    }

    public by getNBT() {
        by compound = new by();
        this.dialogData.writeNBT(compound);
        this.bankData.writeNBT(compound);
        this.questData.writeNBT(compound);
        this.transportData.writeNBT(compound);
        this.factionData.writeNBT(compound);
        this.itemgiverData.writeNBT(compound);
        this.mailData.writeNBT(compound);
        return compound;
    }

    public void saveNBTData(by compound) {
        PlayerDataController.instance.savePlayerData(this.playerName, this.getNBT());
    }

    public void loadNBTData(by compound) {
    }

    public void init(nn entity, abw world) {
    }
}


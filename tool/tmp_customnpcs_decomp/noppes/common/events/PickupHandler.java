/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.IPickupNotifier
 *  ss
 *  uf
 */
package noppes.common.events;

import cpw.mods.fml.common.IPickupNotifier;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;

public class PickupHandler
implements IPickupNotifier {
    public void notifyPickup(ss item, uf player) {
        if (!player.q.I) {
            PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
            playerdata.checkQuestCompletion(player, EnumQuestType.Item);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  uy
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.InventoryNPC;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.containers.SlotNpcBankCurrency;
import noppes.common.controllers.PlayerBankData;
import noppes.common.controllers.PlayerDataController;

public class ContainerNPCBankInterface
extends uy {
    public InventoryNPC currencyMatrix;
    public SlotNpcBankCurrency currency;
    public int slot = 0;
    public int bankid;
    private final uf player;
    private PlayerBankData data;

    public ContainerNPCBankInterface(uf player, int slot, int bankid) {
        int j1;
        int l;
        this.bankid = bankid;
        this.slot = slot;
        this.player = player;
        this.currencyMatrix = new InventoryNPC("currency", 1, this);
        if (!this.isAvailable() || this.canBeUpgraded()) {
            this.currency = new SlotNpcBankCurrency(this, this.currencyMatrix, 0, 80, 29);
            this.a(this.currency);
        }
        NpcMiscInventory items = new NpcMiscInventory(54);
        if (!player.q.I) {
            this.data = PlayerDataController.instance.getBankData(player, bankid);
            items = this.data.getBankOrDefault((int)bankid).itemSlots.get(slot);
        }
        int xOffset = this.xOffset();
        for (l = 0; l < this.getRowNumber(); ++l) {
            for (j1 = 0; j1 < 9; ++j1) {
                int id = j1 + l * 9;
                this.a(new we((mo)items, id, 8 + j1 * 18, 17 + xOffset + l * 18));
            }
        }
        if (this.isUpgraded()) {
            xOffset += 54;
        }
        for (l = 0; l < 3; ++l) {
            for (j1 = 0; j1 < 9; ++j1) {
                this.a(new we((mo)player.bn, j1 + l * 9 + 9, 8 + j1 * 18, 86 + xOffset + l * 18));
            }
        }
        for (l = 0; l < 9; ++l) {
            this.a(new we((mo)player.bn, l, 8 + l * 18, 144 + xOffset));
        }
    }

    public int getRowNumber() {
        return 0;
    }

    public int xOffset() {
        return 0;
    }

    public void a(mo inv) {
    }

    public boolean isAvailable() {
        return false;
    }

    public boolean isUpgraded() {
        return false;
    }

    public boolean canBeUpgraded() {
        return false;
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public void b(uf entityplayer) {
        super.b(entityplayer);
        if (!entityplayer.q.I) {
            ye var3 = this.currencyMatrix.a(0);
            this.currencyMatrix.a(0, null);
            if (var3 != null) {
                entityplayer.b(var3);
            }
        }
    }
}


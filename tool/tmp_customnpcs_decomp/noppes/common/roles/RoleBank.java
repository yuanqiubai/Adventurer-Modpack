/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.roles;

import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.controllers.BankData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;

public class RoleBank
extends RoleInterface {
    public int bankId = -1;

    public RoleBank(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("RoleBankID", this.bankId);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.bankId = nbttagcompound.e("RoleBankID");
    }

    @Override
    public boolean interact(uf player) {
        BankData data = PlayerDataController.instance.getBankData(player, this.bankId).getBankOrDefault(this.bankId);
        data.openBankGui(player, this.npc, this.bankId, 0);
        this.npc.say(player, this.npc.advanced.getInteractLine());
        return false;
    }

    public Bank getBank() {
        Bank bank = BankController.getInstance().banks.get(this.bankId);
        return bank != null ? bank : BankController.getInstance().banks.values().iterator().next();
    }
}


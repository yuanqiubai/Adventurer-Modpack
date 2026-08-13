/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.controllers.BankData;
import noppes.common.controllers.IPlayerData;

public class PlayerBankData
implements IPlayerData {
    public HashMap<Integer, BankData> banks = new HashMap();

    public void readNBT(by loadPlayerData) {
        HashMap<Integer, BankData> banks = new HashMap<Integer, BankData>();
        cg list = loadPlayerData.m("BankData");
        if (list != null && list.c() != 0) {
            for (int i = 0; i < list.c(); ++i) {
                by nbttagcompound = (by)list.b(i);
                BankData data = new BankData();
                data.readNBT(nbttagcompound);
                banks.put(data.bankId, data);
            }
        }
        this.banks = banks;
    }

    @Override
    public by writeNBT(by playerData) {
        cg list = new cg();
        for (BankData data : this.banks.values()) {
            by nbttagcompound = new by();
            data.writeNBT(nbttagcompound);
            list.a((cl)nbttagcompound);
        }
        playerData.a("BankData", (cl)list);
        return playerData;
    }

    public BankData getBank(int bankId) {
        return this.banks.get(bankId);
    }

    public BankData getBankOrDefault(int bankId) {
        BankData data = this.banks.get(bankId);
        if (data != null) {
            return data;
        }
        Bank bank = BankController.getInstance().getBank(bankId);
        return this.banks.get(bank.id);
    }

    public boolean hasBank(int bank) {
        return this.banks.containsKey(bank);
    }

    public void loadNew(int bank) {
        BankData data = new BankData();
        data.bankId = bank;
        this.banks.put(bank, data);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  ci
 *  cl
 */
package noppes.common.controllers;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import noppes.common.controllers.Bank;
import noppes.util.Tools;

public class BankController {
    private static BankController instance;
    public HashMap<Integer, Bank> banks;
    private String filePath = "";

    public BankController() {
        instance = this;
        this.banks = new HashMap();
        this.loadBanks();
        if (this.banks.isEmpty()) {
            Bank bank = new Bank();
            bank.id = 0;
            bank.name = "Default Bank";
            for (int i = 0; i < 6; ++i) {
                bank.slotTypes.put(i, 0);
            }
            this.banks.put(bank.id, bank);
        }
    }

    public static BankController getInstance() {
        if (BankController.newInstance()) {
            instance = new BankController();
        }
        return instance;
    }

    private static boolean newInstance() {
        if (instance == null) {
            return true;
        }
        File file = Tools.getWorldSaveDirectory();
        return file != null && !BankController.instance.filePath.equals(file.getAbsolutePath());
    }

    private void loadBanks() {
        File saveDir = Tools.getWorldSaveDirectory();
        if (saveDir != null) {
            this.filePath = saveDir.getAbsolutePath();
            try {
                File e = new File(saveDir, "bank.dat");
                if (e.exists()) {
                    this.loadBanks(e);
                }
            }
            catch (Exception var5) {
                try {
                    File ee = new File(saveDir, "bank.dat_old");
                    if (ee.exists()) {
                        this.loadBanks(ee);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    private void loadBanks(File file) throws IOException {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
        this.loadBanks(var1);
        var1.close();
    }

    public void loadBanks(DataInputStream stream) throws IOException {
        HashMap<Integer, Bank> banks = new HashMap<Integer, Bank>();
        by nbt = ci.a((DataInput)stream);
        cg list = nbt.m("Data");
        if (list != null) {
            for (int i = 0; i < list.c(); ++i) {
                by nbttagcompound = (by)list.b(i);
                Bank bank = new Bank();
                bank.readEntityFromNBT(nbttagcompound);
                banks.put(bank.id, bank);
            }
        }
        this.banks = banks;
    }

    public by getNBT() {
        cg list = new cg();
        for (Bank bank : this.banks.values()) {
            by nbtfactions = new by();
            bank.writeEntityToNBT(nbtfactions);
            list.a((cl)nbtfactions);
        }
        by nbttagcompound1 = new by();
        nbttagcompound1.a("Data", (cl)list);
        return nbttagcompound1;
    }

    public Bank getBank(int bankId) {
        Bank bank = this.banks.get(bankId);
        return bank != null ? bank : this.banks.values().iterator().next();
    }

    public void saveBanks() {
        try {
            File e = Tools.getWorldSaveDirectory();
            File file = new File(e, "bank.dat_new");
            File file1 = new File(e, "bank.dat_old");
            File file2 = new File(e, "bank.dat");
            ci.a((by)this.getNBT(), (OutputStream)new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public void saveBank(Bank bank) {
        if (bank.id < 0) {
            bank.id = this.getUnusedId();
        }
        this.banks.put(bank.id, bank);
        this.saveBanks();
    }

    public int getUnusedId() {
        int id = 0;
        while (this.banks.containsKey(id)) {
            ++id;
        }
        return id;
    }

    public void removeBank(int bank) {
        if (bank >= 0 && this.banks.size() > 1) {
            this.banks.remove(bank);
            this.saveBanks();
        }
    }
}


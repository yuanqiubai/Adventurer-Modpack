/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 *  cl
 *  jv
 *  net.minecraft.server.MinecraftServer
 *  net.minecraftforge.common.IExtendedEntityProperties
 *  org.apache.commons.lang3.RandomStringUtils
 *  uf
 */
package noppes.common.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.IExtendedEntityProperties;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.controllers.PlayerBankData;
import noppes.common.controllers.PlayerData;
import noppes.common.controllers.PlayerMail;
import noppes.util.Tools;
import org.apache.commons.lang3.RandomStringUtils;

public class PlayerDataController {
    private static final String id = RandomStringUtils.random((int)12, (boolean)true, (boolean)true);
    public static PlayerDataController instance;

    public PlayerDataController() {
        instance = this;
    }

    public File getSaveDir() {
        try {
            File e = new File(Tools.getWorldSaveDirectory(), "playerdata");
            if (!e.exists()) {
                e.mkdir();
            }
            return e;
        }
        catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public by loadPlayerData(String player) {
        File e;
        File saveDir = this.getSaveDir();
        String filename = player;
        if (player.isEmpty()) {
            filename = "noplayername";
        }
        filename = filename + ".dat";
        try {
            e = new File(saveDir, filename);
            if (e.exists()) {
                return ci.a((InputStream)new FileInputStream(e));
            }
        }
        catch (Exception var6) {
            System.err.println(var6.getMessage());
        }
        try {
            e = new File(saveDir, filename + "_old");
            if (e.exists()) {
                return ci.a((InputStream)new FileInputStream(e));
            }
        }
        catch (Exception var5) {
            System.err.println(var5.getMessage());
        }
        return new by();
    }

    public void savePlayerData(String username, by compound) {
        String filename = username;
        if (username.isEmpty()) {
            filename = "noplayername";
        } else {
            jv e = MinecraftServer.F().af().f(username);
            if (e != null) {
                e.getEntityData().a("CustomNpcsId", id);
                e.getEntityData().a("CustomNpcsData", (cl)compound);
            }
        }
        filename = filename + ".dat";
        try {
            File e1 = this.getSaveDir();
            File file = new File(e1, filename + "_new");
            File file1 = new File(e1, filename + "_old");
            File file2 = new File(e1, filename);
            ci.a((by)compound, (OutputStream)new FileOutputStream(file));
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
        catch (Exception var8) {
            System.err.println(var8.getMessage());
        }
    }

    public PlayerBankData getBankData(uf player, int bankId) {
        Bank bank = BankController.getInstance().getBank(bankId);
        PlayerBankData data = this.getPlayerData((uf)player).bankData;
        if (!data.hasBank(bank.id)) {
            data.loadNew(bank.id);
        }
        return data;
    }

    public PlayerData getPlayerData(uf player) {
        PlayerData data = (PlayerData)player.getExtendedProperties("CustomNpcsData");
        if (data == null) {
            data = new PlayerData(player.c_());
            player.registerExtendedProperties("CustomNpcsData", (IExtendedEntityProperties)data);
        }
        return data;
    }

    public String hasPlayer(String username) {
        String[] arr$;
        for (String file : arr$ = this.getSaveDir().list()) {
            if (!file.equalsIgnoreCase(username + ".dat")) continue;
            return file.substring(0, file.length() - 4);
        }
        return "";
    }

    public void addPlayerMessage(String username, PlayerMail mail) {
        mail.time = System.currentTimeMillis();
        jv player = MinecraftServer.F().af().f(username);
        PlayerData data = player == null ? new PlayerData(username) : this.getPlayerData((uf)player);
        data.mailData.playermail.add(mail);
        this.savePlayerData(username, data.getNBT());
    }

    public boolean hasMail(uf player) {
        return this.getPlayerData((uf)player).mailData.hasMail();
    }
}


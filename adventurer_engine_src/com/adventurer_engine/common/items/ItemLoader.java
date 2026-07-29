/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  ci
 *  com.google.gson.internal.StringMap
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.registry.GameRegistry
 *  yc
 */
package com.adventurer_engine.common.items;

import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.gadgets.RangeAttackPacket;
import com.adventurer_engine.common.items.TabsLoader;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.BasicItemSword;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.gadget.ItemBackpack;
import com.adventurer_engine.common.items.gadget.ItemDuring;
import com.adventurer_engine.common.items.gadget.ItemGreenRing;
import com.adventurer_engine.common.items.gadget.ItemLifeGem;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ItemLoader {
    public static final List<yc> item = new ArrayList<yc>();
    public static final StringMap<BasicAmmo> ammoItem = new StringMap();
    public static ItemBackpack itemBackpack;
    public static ItemDuring itemDuring;
    public static ItemLifeGem itemLifeGem;
    public static ItemGreenRing itemGreenRing;
    private static final String CERT_SHA256 = "f574c6b8f03df8eb016a531c269b3dc1af4a7c275ef0f04b9274edecc5c22c2a";

    private static String computeSHA256(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(data);
            return ItemLoader.byteHex2String(digest);
        }
        catch (Exception ignored) {
            return "";
        }
    }

    private static String byteHex2String(byte[] data) {
        BigInteger number = new BigInteger(1, data);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static byte[] AESDecrypt(byte[] encryptedData) throws Exception {
        byte[] key = new byte[16];
        System.arraycopy(encryptedData, 0, key, 0, 16);
        byte[] iv = new byte[16];
        System.arraycopy(encryptedData, 16, iv, 0, 16);
        byte[] data = new byte[encryptedData.length - 32];
        System.arraycopy(encryptedData, 32, data, 0, data.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(2, (Key)secretKeySpec, ivParameterSpec);
        return cipher.doFinal(data);
    }

    private static void registerGadgetItems() {
        itemBackpack = new ItemBackpack();
        GameRegistry.registerItem((yc)itemBackpack, (String)itemBackpack.a());
        itemDuring = new ItemDuring();
        GameRegistry.registerItem((yc)itemDuring, (String)itemDuring.a());
        itemLifeGem = new ItemLifeGem();
        GameRegistry.registerItem((yc)itemLifeGem, (String)itemLifeGem.a());
        itemGreenRing = new ItemGreenRing();
        GameRegistry.registerItem((yc)itemGreenRing, (String)itemGreenRing.a());
    }

    public static void load(FMLPreInitializationEvent event) {
        ItemLoader.registerGadgetItems();
        try {
            InputStream in = ItemLoader.class.getResourceAsStream("/test.dat");
            if (in == null) {
                return;
            }
            byte[] originData = new byte[in.available()];
            in.read(originData);
            in.close();
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(originData));
            by nbt = ci.a((DataInput)inputStream);
            DebugGadgets.debugInfo("Read NBTs");
            ItemLoader.loadNBT(nbt);
        }
        catch (Exception e) {
            DebugGadgets.debugInfo(e.toString());
            e.printStackTrace();
        }
    }

    private static void loadJson(FMLPreInitializationEvent event) {
        File itemPath = new File(event.getModConfigurationDirectory().getParentFile().getPath() + File.separator + "RCS" + File.separator + "items");
        BasicItem newItem = null;
        for (File f : itemPath.listFiles()) {
            JsonHelper json = new JsonHelper(f);
            List itemDatas = json.getList("items");
            String module = json.getString("module");
            for (StringMap data : itemDatas) {
                JsonHelper itemData = new JsonHelper(data);
                String itemType = itemData.getString("type");
                if ("normal".equals(itemType)) {
                    newItem = new BasicItem(CommonConfig.itemStartID++, itemData, module);
                } else if ("sword".equals(itemType)) {
                    newItem = new BasicItemSword(CommonConfig.itemStartID++, itemData, module);
                } else if ("ranged_weapon".equals(itemType)) {
                    newItem = new BasicRangedWeapon(CommonConfig.itemStartID++, itemData, module);
                } else if ("ammo".equals(itemType)) {
                    newItem = new BasicAmmo(CommonConfig.itemStartID++, itemData, module);
                }
                if (newItem == null) continue;
                item.add(newItem);
                GameRegistry.registerItem((yc)newItem, (String)newItem.a());
            }
        }
    }

    private static void loadNBT(by nbt) {
        BasicItem newItem;
        int i;
        cg ls2;
        DebugGadgets.debugInfo("Try to Load Item data");
        if (!nbt.b("root")) {
            return;
        }
        nbt = nbt.l("root");
        TabsLoader.load(nbt);
        DebugGadgets.debugInfo("load tabs done.");
        if (nbt.b("AttackPacket")) {
            ls2 = nbt.m("AttackPacket");
            for (i = 0; i < ls2.c(); ++i) {
                new DamagePacket((by)ls2.b(i));
            }
        }
        DebugGadgets.debugInfo("load AttackPacket done.");
        if (nbt.b("FireArmAttribute")) {
            ls2 = nbt.m("FireArmAttribute");
            for (i = 0; i < ls2.c(); ++i) {
                new RangeAttackPacket((by)ls2.b(i));
            }
        }
        DebugGadgets.debugInfo("load FireArmAttribute done.");
        if (nbt.b("Item")) {
            ls2 = nbt.m("Item");
            for (i = 0; i < ls2.c(); ++i) {
                newItem = new BasicItem((by)ls2.b(i));
                item.add(newItem);
                GameRegistry.registerItem((yc)newItem, (String)newItem.a());
            }
        }
        DebugGadgets.debugInfo("load Item done.");
        if (nbt.b("AMMO")) {
            ls2 = nbt.m("AMMO");
            for (i = 0; i < ls2.c(); ++i) {
                newItem = new BasicAmmo((by)ls2.b(i));
                item.add(newItem);
                GameRegistry.registerItem((yc)newItem, (String)newItem.a());
            }
        }
        DebugGadgets.debugInfo("load AMMO done.");
        if (nbt.b("ColdWeapon")) {
            ls2 = nbt.m("ColdWeapon");
            for (i = 0; i < ls2.c(); ++i) {
                newItem = new BasicItemSword((by)ls2.b(i));
                item.add(newItem);
                GameRegistry.registerItem((yc)newItem, (String)newItem.a());
            }
        }
        DebugGadgets.debugInfo("load ColdWeapon done.");
        if (nbt.b("Gun")) {
            ls2 = nbt.m("Gun");
            for (i = 0; i < ls2.c(); ++i) {
                newItem = new BasicRangedWeapon((by)ls2.b(i));
                item.add(newItem);
                GameRegistry.registerItem((yc)newItem, (String)newItem.a());
            }
        }
        DebugGadgets.debugInfo("load Gun done.");
    }
}


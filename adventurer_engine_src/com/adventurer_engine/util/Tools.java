/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  atv
 *  bkb
 *  by
 *  cb
 *  cd
 *  cg
 *  ci
 *  ck
 *  cl
 *  cm
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ea
 *  net.minecraft.server.MinecraftServer
 *  nj
 *  nn
 *  uf
 */
package com.adventurer_engine.util;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import net.minecraft.server.MinecraftServer;

public class Tools {
    public static File debugFile;
    public static PrintStream out;
    public static final Random random;

    public static boolean percent(float p) {
        return new Random().nextFloat() < p;
    }

    public static void debugInfo(String info) {
        if (out == null) {
            try {
                out = new PrintStream(debugFile);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        out.println(info);
    }

    public static nj[] intArr2PotionArr(int[] array) {
        nj[] toRet = new nj[array.length / 3];
        for (int i = 0; i < toRet.length; ++i) {
            toRet[i] = new nj(array[3 * i], array[3 * i + 1], array[3 * i + 2]);
        }
        return toRet;
    }

    public static void playSoundAtEntity(abw world, nn entity, String sound, float f, float add) {
        float volume = world.s.nextFloat() * f + add;
        float pitch = 1.0f / (world.s.nextFloat() * f + add);
        world.a(entity, sound, volume, pitch);
    }

    public static String formatCalendar(long value) {
        long day = value / 24000L;
        long hour = value % 24000L / 1200L;
        long minute = value % 1200L / 20L;
        return String.format(bkb.a((String)"adventurer_engine.calendar"), day / 360L, day % 360L / 30L + 1L, day % 30L + 1L, hour, minute);
    }

    public static File getWorldSaveDirectory() {
        MinecraftServer server = MinecraftServer.F();
        File saves = new File(".");
        if (server != null && !server.V()) {
            saves = new File(atv.w().x, "saves");
        }
        if (server != null) {
            File savedir = new File(new File(saves, server.L()), "adventurer_engine");
            if (!savedir.exists()) {
                savedir.mkdir();
            }
            return savedir;
        }
        return null;
    }

    public static String randomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void handlePacket(cm manager, ea packet, Player player) {
        try {
            uf entityPlayer = (uf)player;
            ByteArrayDataInput in = ByteStreams.newDataInput((byte[])packet.c);
            int id = in.readInt();
            RCsPacket p = RCsPacket.packetFactory(id);
            p.process(in, entityPlayer, entityPlayer.q.I ? Side.CLIENT : Side.SERVER);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void writeNBT(ByteArrayDataOutput out, by nbt) throws IOException {
        byte[] nbt_data = ci.a((by)nbt);
        out.writeInt(nbt_data.length);
        out.write(nbt_data);
    }

    public static by readNBT(ByteArrayDataInput in) throws IOException {
        int len = in.readInt();
        byte[] nbt_data = new byte[len];
        in.readFully(nbt_data);
        return ci.a((byte[])nbt_data);
    }

    public static double[] readDoubleArrayFromNBT(by nbt, String key) {
        cg ls2 = nbt.m(key);
        double[] toRet = new double[ls2.c()];
        for (int i = 0; i < ls2.c(); ++i) {
            toRet[i] = ((cb)ls2.b((int)i)).a;
        }
        return toRet;
    }

    public static void writeDoubleArray2NBT(by nbt, double[] arr, String key) {
        cg ls2 = new cg();
        for (double d : arr) {
            ls2.a((cl)new cb(null, d));
        }
        nbt.a(key, (cl)ls2);
    }

    public static float[] readFloatArrayFromNBT(by nbt, String key) {
        cg ls2 = nbt.m(key);
        float[] toRet = new float[ls2.c()];
        for (int i = 0; i < ls2.c(); ++i) {
            toRet[i] = ((cd)ls2.b((int)i)).a;
        }
        return toRet;
    }

    public static String[] readStringArrayFromNBT(by nbt, String key) {
        cg ls2 = nbt.m(key);
        String[] toRet = new String[ls2.c()];
        for (int i = 0; i < ls2.c(); ++i) {
            toRet[i] = ((ck)ls2.b((int)i)).a;
        }
        return toRet;
    }

    public static <T> T randomSelect(List<T> ls2, int[] weight) {
        int len = Math.min(ls2.size(), weight.length);
        if (len == 0) {
            return null;
        }
        int sum = 0;
        for (int i = 0; i < len; ++i) {
            sum += weight[i];
        }
        if (sum == 0) {
            return null;
        }
        int r = random.nextInt(sum);
        int cur = 0;
        for (int i = 0; i < len; ++i) {
            if (r >= (cur += weight[i])) continue;
            return ls2.get(i);
        }
        return null;
    }

    public static String calculateSHA256(File file) {
        try {
            int bytesRead;
            FileInputStream fis = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteArray = new byte[1024];
            while ((bytesRead = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesRead);
            }
            byte[] hashBytes = digest.digest();
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        }
        catch (IOException e) {
            return null;
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    static {
        random = new Random();
    }
}


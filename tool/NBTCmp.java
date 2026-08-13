import java.io.*;
import java.util.*;
import net.minecraft.nbt.*;

public class NBTCmp {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java NBTCmp <ref_level.dat> <test_level.dat>");
            return;
        }

        NBTTagCompound ref = read(args[0]);
        NBTTagCompound test = read(args[1]);

        NBTTagCompound refData = ref.getCompoundTag("Data");
        NBTTagCompound testData = test.getCompoundTag("Data");

        // Compare top-level Data fields (non-compound, non-list)
        System.out.println("=== Data 顶层字段对比 ===");
        System.out.println(String.format("%-30s %-30s %-30s", "Key", "Reference(V1.9071)", "Test(V1.91)"));
        System.out.println(repeat("-", 90));

        Set<String> allKeys = new TreeSet<>();
        for (Object k : getKeys(refData)) allKeys.add((String)k);
        for (Object k : getKeys(testData)) allKeys.add((String)k);

        for (String key : allKeys) {
            Object rv = getSimple(refData, key);
            Object tv = getSimple(testData, key);
            if (rv == null && tv == null) continue;
            String rvs = fmt(rv);
            String tvs = fmt(tv);
            String mark = rvs.equals(tvs) ? "" : " <-- DIFF";
            if (rv == null) mark = " <-- missing in ref";
            if (tv == null) mark = " <-- missing in test";
            System.out.println(String.format("%-30s %-30s %-30s%s", key, rvs, tvs, mark));
        }

        // GameRules
        System.out.println("\n=== GameRules ===");
        System.out.println(String.format("%-28s %-20s %-20s", "Rule", "Reference", "Test"));
        System.out.println(repeat("-", 70));

        NBTTagCompound refGR = refData.getCompoundTag("GameRules");
        NBTTagCompound testGR = testData.getCompoundTag("GameRules");
        Set<String> grKeys = new TreeSet<>();
        for (Object k : getKeys(refGR)) grKeys.add((String)k);
        for (Object k : getKeys(testGR)) grKeys.add((String)k);

        for (String key : grKeys) {
            String rv = refGR.hasKey(key) ? refGR.getString(key) : "(none)";
            String tv = testGR.hasKey(key) ? testGR.getString(key) : "(none)";
            String mark = rv.equals(tv) ? "" : " <--";
            System.out.println(String.format("%-28s %-20s %-20s%s", key, rv, tv, mark));
        }
    }

    static NBTTagCompound read(String path) throws Exception {
        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            return (NBTTagCompound) CompressedStreamTools.readCompressed(in);
        }
    }

    static Set getKeys(NBTTagCompound tag) {
        // Use reflection since Minecraft 1.6.4 NBT doesn't expose keySet easily
        // Just hardcode the keys we care about, or use the tagMap field
        try {
            java.lang.reflect.Field f = NBTTagCompound.class.getDeclaredField("tagMap");
            f.setAccessible(true);
            return ((Map)f.get(tag)).keySet();
        } catch (Exception e) {
            return new HashSet();
        }
    }

    static Object getSimple(NBTTagCompound tag, String key) {
        if (!tag.hasKey(key)) return null;
        NBTBase base = tag.getTag(key);
        if (base instanceof NBTTagString) return ((NBTTagString)base).data;
        if (base instanceof NBTTagInt) return ((NBTTagInt)base).data;
        if (base instanceof NBTTagLong) return ((NBTTagLong)base).data;
        if (base instanceof NBTTagByte) return ((NBTTagByte)base).data;
        if (base instanceof NBTTagShort) return ((NBTTagShort)base).data;
        if (base instanceof NBTTagFloat) return ((NBTTagFloat)base).data;
        if (base instanceof NBTTagDouble) return ((NBTTagDouble)base).data;
        return "(compound)";
    }

    static String fmt(Object v) {
        if (v == null) return "(none)";
        return String.valueOf(v);
    }

    static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }
}

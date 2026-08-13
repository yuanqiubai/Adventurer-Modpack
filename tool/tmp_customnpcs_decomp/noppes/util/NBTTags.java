/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cf
 *  cg
 *  cl
 *  ye
 */
package noppes.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class NBTTags {
    public static HashMap<Integer, ye> getItemStackList(cg tagList) {
        HashMap<Integer, ye> list = new HashMap<Integer, ye>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            try {
                list.put(nbttagcompound.c("Slot") & 0xFF, ye.a((by)nbttagcompound));
                continue;
            }
            catch (ClassCastException var5) {
                list.put(nbttagcompound.e("Slot"), ye.a((by)nbttagcompound));
            }
        }
        return list;
    }

    public static ye[] getItemStackArray(cg tagList) {
        ye[] list = new ye[tagList.c()];
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list[nbttagcompound.c((String)"Slot") & 0xFF] = ye.a((by)nbttagcompound);
        }
        return list;
    }

    public static ArrayList<int[]> getIntegerArraySet(cg tagList) {
        ArrayList<int[]> set = new ArrayList<int[]>();
        for (int i = 0; i < tagList.c(); ++i) {
            cg list = (cg)tagList.b(i);
            int[] arr = new int[list.c()];
            for (int j = 0; j < list.c(); ++j) {
                by nbttagcompound = (by)list.b(j);
                arr[j] = nbttagcompound.e("Slot");
            }
            set.add(arr);
        }
        return set;
    }

    public static HashMap<Integer, Boolean> getBooleanList(cg tagList) {
        HashMap<Integer, Boolean> list = new HashMap<Integer, Boolean>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.e("Slot"), nbttagcompound.n("Boolean"));
        }
        return list;
    }

    public static HashMap<Integer, Integer> getIntegerIntegerMap(cg tagList) {
        HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.e("Slot"), nbttagcompound.e("Integer"));
        }
        return list;
    }

    public static HashMap<Integer, Long> getIntegerLongMap(cg tagList) {
        HashMap<Integer, Long> list = new HashMap<Integer, Long>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.e("Slot"), nbttagcompound.f("Long"));
        }
        return list;
    }

    public static HashSet<Integer> getIntegerSet(cg tagList) {
        HashSet<Integer> list = new HashSet<Integer>();
        for (int i = 0; i < tagList.c(); ++i) {
            cl base = tagList.b(i);
            if (base instanceof cf) {
                list.add(((cf)base).a);
                continue;
            }
            by nbttagcompound = (by)tagList.b(i);
            list.add(nbttagcompound.e("Integer"));
        }
        return list;
    }

    public static HashMap<String, String> getStringStringMap(cg tagList) {
        HashMap<String, String> list = new HashMap<String, String>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.i("Slot"), nbttagcompound.i("Value"));
        }
        return list;
    }

    public static HashMap<Integer, String> getIntegerStringMap(cg tagList) {
        HashMap<Integer, String> list = new HashMap<Integer, String>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.e("Slot"), nbttagcompound.i("Value"));
        }
        return list;
    }

    public static HashMap<String, Integer> getStringIntegerMap(cg tagList) {
        HashMap<String, Integer> list = new HashMap<String, Integer>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            list.put(nbttagcompound.i("Slot"), nbttagcompound.e("Value"));
        }
        return list;
    }

    public static HashMap<String, Vector<String>> getVectorMap(cg tagList) {
        HashMap<String, Vector<String>> map = new HashMap<String, Vector<String>>();
        for (int i = 0; i < tagList.c(); ++i) {
            Vector<String> values = new Vector<String>();
            by nbttagcompound = (by)tagList.b(i);
            cg list = nbttagcompound.m("Values");
            for (int j = 0; j < list.c(); ++j) {
                by value = (by)list.b(j);
                values.add(value.i("Value"));
            }
            map.put(nbttagcompound.i("Key"), values);
        }
        return map;
    }

    public static int[] getIntArray(cg list) {
        int[] inta = new int[list.c()];
        for (int i = 0; i < list.c(); ++i) {
            inta[i] = ((cf)list.b((int)i)).a;
        }
        return inta;
    }

    public static List<String> getStringList(cg tagList) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            String line = nbttagcompound.i("Line");
            list.add(line);
        }
        return list;
    }

    public static String[] getStringArray(cg tagList, int size) {
        String[] arr = new String[size];
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            String line = nbttagcompound.i("Value");
            int slot = nbttagcompound.e("Slot");
            arr[slot] = line;
        }
        return arr;
    }

    public static cg nbtIntegerArraySet(List<int[]> set) {
        cg nbttaglist = new cg();
        if (set == null) {
            return nbttaglist;
        }
        for (int[] arr : set) {
            cg list = new cg();
            for (int i : arr) {
                by nbttagcompound = new by();
                nbttagcompound.a("Slot", i);
                list.a((cl)nbttagcompound);
            }
            nbttaglist.a((cl)list);
        }
        return nbttaglist;
    }

    public static cg nbtItemStackList(HashMap<Integer, ye> inventory) {
        cg nbttaglist = new cg();
        if (inventory != null) {
            for (Integer integer : inventory.keySet()) {
                int slot = integer;
                ye item = inventory.get(slot);
                if (item == null) continue;
                by nbttagcompound = new by();
                nbttagcompound.a("Slot", (byte)slot);
                item.b(nbttagcompound);
                nbttaglist.a((cl)nbttagcompound);
            }
        }
        return nbttaglist;
    }

    public static cg nbtItemStackArray(ye[] inventory) {
        cg nbttaglist = new cg();
        if (inventory == null) {
            return nbttaglist;
        }
        for (int slot = 0; slot < inventory.length; ++slot) {
            ye item = inventory[slot];
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", (byte)slot);
            if (item != null) {
                item.b(nbttagcompound);
            }
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtBooleanList(HashMap<Integer, Boolean> updatedSlots) {
        cg nbttaglist = new cg();
        if (updatedSlots == null) {
            return nbttaglist;
        }
        HashMap<Integer, Boolean> inventory2 = updatedSlots;
        for (Integer slot : updatedSlots.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot.intValue());
            nbttagcompound.a("Boolean", inventory2.get(slot).booleanValue());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtIntegerIntegerMap(HashMap<Integer, Integer> lines) {
        cg nbttaglist = new cg();
        if (lines == null) {
            return nbttaglist;
        }
        for (Integer slot : lines.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot.intValue());
            nbttagcompound.a("Integer", lines.get(slot).intValue());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtIntegerLongMap(HashMap<Integer, Long> lines) {
        cg nbttaglist = new cg();
        if (lines == null) {
            return nbttaglist;
        }
        for (Integer slot : lines.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot.intValue());
            nbttagcompound.a("Long", lines.get(slot).longValue());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtIntegerSet(HashSet<Integer> set) {
        cg nbttaglist = new cg();
        if (set == null) {
            return nbttaglist;
        }
        for (Integer integer : set) {
            int slot = integer;
            by nbttagcompound = new by();
            nbttagcompound.a("Integer", slot);
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtVectorMap(HashMap<String, Vector<String>> map) {
        cg list = new cg();
        if (map == null) {
            return list;
        }
        for (String key : map.keySet()) {
            by compound = new by();
            compound.a("Key", key);
            cg values = new cg();
            for (String o : map.get(key)) {
                by comp = new by();
                comp.a("Value", o);
                values.a((cl)comp);
            }
            compound.a("Values", (cl)values);
            list.a((cl)compound);
        }
        return list;
    }

    public static cg nbtStringStringMap(HashMap<String, String> map) {
        cg nbttaglist = new cg();
        if (map == null) {
            return nbttaglist;
        }
        for (String slot : map.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            nbttagcompound.a("Value", map.get(slot));
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtStringIntegerMap(HashMap<String, Integer> map) {
        cg nbttaglist = new cg();
        if (map == null) {
            return nbttaglist;
        }
        for (String slot : map.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            nbttagcompound.a("Value", map.get(slot).intValue());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cl nbtIntegerStringMap(HashMap<Integer, String> map) {
        cg nbttaglist = new cg();
        if (map == null) {
            return nbttaglist;
        }
        for (Integer integer : map.keySet()) {
            int slot = integer;
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            nbttagcompound.a("Value", map.get(slot));
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtStringArray(String[] list) {
        cg nbttaglist = new cg();
        if (list == null) {
            return nbttaglist;
        }
        for (int i = 0; i < list.length; ++i) {
            if (list[i] == null) continue;
            by nbttagcompound = new by();
            nbttagcompound.a("Value", list[i]);
            nbttagcompound.a("Slot", i);
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public static cg nbtIntArray(int[] ad2) {
        cg nbttaglist = new cg();
        for (int i : ad2) {
            nbttaglist.a((cl)new cf(null, i));
        }
        return nbttaglist;
    }

    public static cg nbtStringList(List<String> list) {
        cg nbttaglist = new cg();
        for (String s : list) {
            by nbttagcompound = new by();
            nbttagcompound.a("Line", s);
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }
}


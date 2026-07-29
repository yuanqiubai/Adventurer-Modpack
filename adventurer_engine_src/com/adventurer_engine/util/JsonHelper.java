/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bx
 *  by
 *  cd
 *  cg
 *  ch
 *  cj
 *  cl
 *  com.google.gson.Gson
 *  com.google.gson.internal.StringMap
 */
package com.adventurer_engine.util;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JsonHelper {
    private static final Gson JSON_PARSER = new Gson();
    private InputStreamReader in;
    private StringMap root;

    public JsonHelper(InputStreamReader in) {
        this.root = (StringMap)JSON_PARSER.fromJson((Reader)in, StringMap.class);
        this.in = in;
    }

    public JsonHelper(StringMap root) {
        this.root = root;
    }

    public JsonHelper(File f) {
        try {
            FileInputStream in = new FileInputStream(f);
            this.root = (StringMap)JSON_PARSER.fromJson((Reader)new InputStreamReader(in), StringMap.class);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonHelper(InputStream in) {
        this.root = (StringMap)JSON_PARSER.fromJson((Reader)new InputStreamReader(in), StringMap.class);
    }

    public boolean hasKey(String key) {
        return this.root.containsKey((Object)key);
    }

    public boolean getBoolean(String key) {
        return (Boolean)this.root.get((Object)key);
    }

    public byte getByte(String key) {
        Object tmp = this.root.get((Object)key);
        if (tmp instanceof String) {
            assert (((String)tmp).endsWith("b"));
            return Byte.parseByte(((String)tmp).substring(0, ((String)tmp).length() - 1));
        }
        return ((Double)tmp).byteValue();
    }

    public short getShort(String key) {
        Object tmp = this.root.get((Object)key);
        if (tmp instanceof String) {
            assert (((String)tmp).endsWith("s"));
            return Short.parseShort(((String)tmp).substring(0, ((String)tmp).length() - 1));
        }
        return ((Double)tmp).shortValue();
    }

    public int getInt(String key) {
        Object tmp = this.root.get((Object)key);
        if (tmp instanceof String) {
            return Integer.parseInt((String)this.root.get((Object)key));
        }
        return ((Double)tmp).intValue();
    }

    public long getLong(String key) {
        Object tmp = this.root.get((Object)key);
        if (tmp instanceof String) {
            assert (((String)tmp).endsWith("l"));
            return Long.parseLong(((String)tmp).substring(0, ((String)tmp).length() - 1));
        }
        return ((Double)tmp).longValue();
    }

    public float getFloat(String key) {
        Object tmp = this.root.get((Object)key);
        if (tmp instanceof String) {
            assert (((String)tmp).endsWith("f"));
            return Float.parseFloat(((String)tmp).substring(0, ((String)tmp).length() - 1));
        }
        return ((Double)tmp).floatValue();
    }

    public double getDouble(String key) {
        Object o = this.root.get((Object)key);
        if (o instanceof Double) {
            return (Double)o;
        }
        if (o instanceof String) {
            try {
                return Double.parseDouble((String)o);
            }
            catch (Exception e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    public String getString(String key) {
        return (String)this.root.get((Object)key);
    }

    public List getList(String key) {
        return (List)this.root.get((Object)key);
    }

    public List<JsonHelper> getJsonList(String key) {
        List ls2 = this.getList(key);
        LinkedList<JsonHelper> ret = new LinkedList<JsonHelper>();
        for (Object o : ls2) {
            if (!(o instanceof StringMap)) continue;
            ret.add(new JsonHelper((StringMap)o));
        }
        return ret;
    }

    public int[] getIntArray(String key) {
        List ls2 = this.getList(key);
        LinkedList<Integer> arr = new LinkedList<Integer>();
        for (Object item : ls2) {
            if (!(item instanceof Number)) continue;
            arr.add(((Number)item).intValue());
        }
        int[] ret = new int[arr.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (Integer)arr.get(i);
        }
        return ret;
    }

    public double[] getDoubleArray(String key) {
        List ls2 = this.getList(key);
        LinkedList<Double> arr = new LinkedList<Double>();
        for (Object item : ls2) {
            if (!(item instanceof Number)) continue;
            arr.add((Double)item);
        }
        double[] ret = new double[arr.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (Double)arr.get(i);
        }
        return ret;
    }

    public List<String> getStringArray(String key) {
        List ls2 = this.getList(key);
        LinkedList<String> arr = new LinkedList<String>();
        for (Object item : ls2) {
            if (!(item instanceof String)) continue;
            arr.add((String)item);
        }
        return arr;
    }

    public JsonHelper getSubJson(String key) {
        StringMap map = (StringMap)this.root.get((Object)key);
        return map == null ? null : new JsonHelper(map);
    }

    public Set<String> getKeySet() {
        return this.root.keySet();
    }

    public by toNBT() {
        by nbt = new by("");
        for (String key : this.root.keySet()) {
            Object value = this.root.get((Object)key);
            if (value instanceof String) {
                String str = (String)value;
                if (str.endsWith("b")) {
                    nbt.a(key, Byte.parseByte(str.substring(0, str.length() - 1)));
                    continue;
                }
                if (str.endsWith("s")) {
                    nbt.a(key, Short.parseShort(str.substring(0, str.length() - 1)));
                    continue;
                }
                if (str.endsWith("l")) {
                    nbt.a(key, Long.parseLong(str.substring(0, str.length() - 1)));
                    continue;
                }
                if (str.endsWith("f")) {
                    nbt.a(key, Float.parseFloat(str.substring(0, str.length() - 1)));
                    continue;
                }
                try {
                    int i = Integer.parseInt(str);
                    nbt.a(key, i);
                }
                catch (Exception e) {
                    nbt.a(key, str);
                }
                continue;
            }
            if (value instanceof Double) {
                nbt.a(key, ((Double)value).doubleValue());
                continue;
            }
            if (value instanceof List) {
                List ls2 = (List)value;
                if (ls2.size() <= 0) continue;
                if (ls2.get(0) instanceof Double) {
                    int[] arr = new int[ls2.size()];
                    for (int i = 0; i < arr.length; ++i) {
                        arr[i] = ((Double)ls2.get(i)).intValue();
                    }
                    nbt.a(key, arr);
                    continue;
                }
                cg list = new cg(key);
                if (ls2.get(0) instanceof String) {
                    for (Object o : ls2) {
                        String str = (String)o;
                        if (str.endsWith("b")) {
                            list.a((cl)new bx("", Byte.parseByte(str.substring(0, str.length() - 1))));
                            continue;
                        }
                        if (str.endsWith("s")) {
                            list.a((cl)new cj("", Short.parseShort(str.substring(0, str.length() - 1))));
                            continue;
                        }
                        if (str.endsWith("l")) {
                            list.a((cl)new ch("", Long.parseLong(str.substring(0, str.length() - 1))));
                            continue;
                        }
                        if (!str.endsWith("f")) continue;
                        list.a((cl)new cd("", Float.parseFloat(str.substring(0, str.length() - 1))));
                    }
                } else if (ls2.get(0) instanceof StringMap) {
                    for (Object o : ls2) {
                        list.a((cl)new JsonHelper((StringMap)o).toNBT());
                    }
                }
                nbt.a(key, (cl)list);
                continue;
            }
            if (!(value instanceof StringMap)) continue;
            nbt.a(key, (cl)new JsonHelper((StringMap)value).toNBT());
        }
        return nbt;
    }

    public void close() {
        try {
            this.in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


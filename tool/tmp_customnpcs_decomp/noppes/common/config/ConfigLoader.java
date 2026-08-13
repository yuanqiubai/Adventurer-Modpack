/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.util.DebugGadgets
 *  com.adventurer_engine.util.Tools
 */
package noppes.common.config;

import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import noppes.common.config.ConfigProp;
import noppes.common.config.CustomNPCConfig;

public class ConfigLoader {
    private boolean updateFile = false;
    private final File dir;
    private final String fileName;
    private final Class<?> configClass;
    private final LinkedList<Field> configFields;

    public ConfigLoader(Class<?> clss, File dir, String fileName) {
        Field[] fields;
        while (!dir.exists() && !dir.mkdir()) {
        }
        this.dir = dir;
        this.configClass = clss;
        this.configFields = new LinkedList();
        this.fileName = fileName + ".cfg";
        for (Field field : fields = CustomNPCConfig.class.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigProp.class)) continue;
            this.configFields.add(field);
        }
    }

    public void loadConfig() {
        block7: {
            try {
                File e = new File(this.dir, this.fileName);
                HashMap<String, Field> types = new HashMap<String, Field>();
                for (Field field : this.configFields) {
                    ConfigProp type = field.getAnnotation(ConfigProp.class);
                    types.put(!type.name().isEmpty() ? type.name() : field.getName(), field);
                }
                if (e.exists()) {
                    HashMap<String, Object> fieldValue = this.parseConfig(e, types);
                    for (String fieldName : fieldValue.keySet()) {
                        Field field = types.get(fieldName);
                        Object obj = fieldValue.get(fieldName);
                        if (obj.equals(field.get(null))) continue;
                        field.set(null, obj);
                    }
                    for (String fieldName : types.keySet()) {
                        if (fieldValue.containsKey(fieldName)) continue;
                        this.updateFile = true;
                        break block7;
                    }
                    break block7;
                }
                this.updateFile = true;
            }
            catch (Exception e) {
                this.updateFile = true;
                DebugGadgets.debugInfo((String)e.getMessage());
            }
        }
        if (this.updateFile) {
            this.updateConfig();
        }
        this.updateFile = false;
    }

    private HashMap<String, Object> parseConfig(File file, HashMap<String, Field> types) throws Exception {
        String strLine;
        HashMap<String, Object> config = new HashMap<String, Object>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((strLine = reader.readLine()) != null) {
            if (strLine.startsWith("#") || strLine.length() == 0) continue;
            int index = strLine.indexOf("=");
            if (index > 0) {
                String name = strLine.substring(0, index);
                String prop = strLine.substring(index + 1);
                if (!types.containsKey(name)) {
                    this.updateFile = true;
                    continue;
                }
                Object obj = null;
                Class<Object> fieldType = types.get(name).getType();
                if (fieldType.isAssignableFrom(String.class)) {
                    obj = prop;
                } else if (fieldType.isAssignableFrom(Integer.TYPE)) {
                    obj = Integer.parseInt(prop);
                } else if (fieldType.isAssignableFrom(Short.TYPE)) {
                    obj = Short.parseShort(prop);
                } else if (fieldType.isAssignableFrom(Byte.TYPE)) {
                    obj = Byte.parseByte(prop);
                } else if (fieldType.isAssignableFrom(Boolean.TYPE)) {
                    obj = Boolean.parseBoolean(prop);
                } else if (fieldType.isAssignableFrom(Float.TYPE)) {
                    obj = Float.valueOf(Float.parseFloat(prop));
                } else if (fieldType.isAssignableFrom(Double.TYPE)) {
                    obj = Double.parseDouble(prop);
                }
                if (obj == null) continue;
                config.put(name, obj);
                continue;
            }
            this.updateFile = true;
        }
        reader.close();
        return config;
    }

    public void updateConfig() {
        File file = new File(this.dir, this.fileName);
        try {
            while (!file.exists() && !file.createNewFile()) {
            }
            BufferedWriter e = new BufferedWriter(new FileWriter(file));
            for (Field field : this.configFields) {
                ConfigProp prop = field.getAnnotation(ConfigProp.class);
                if (prop.info().length() != 0) {
                    e.write("#" + prop.info() + System.getProperty("line.separator"));
                }
                String name = !prop.name().isEmpty() ? prop.name() : field.getName();
                try {
                    e.write(name + "=" + field.get(null).toString() + System.getProperty("line.separator"));
                    e.write(System.getProperty("line.separator"));
                }
                catch (IllegalArgumentException exp) {
                    Tools.debugInfo((String)exp.getMessage());
                }
                catch (IllegalAccessException exp) {
                    Tools.debugInfo((String)exp.getMessage());
                }
            }
            e.close();
        }
        catch (IOException exp) {
            Tools.debugInfo((String)exp.getMessage());
        }
    }
}


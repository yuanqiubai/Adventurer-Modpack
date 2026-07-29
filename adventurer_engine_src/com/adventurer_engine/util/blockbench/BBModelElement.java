/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 */
package com.adventurer_engine.util.blockbench;

import com.adventurer_engine.util.INBTable;
import com.adventurer_engine.util.IReadJsonable;
import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.Tools;
import com.adventurer_engine.util.blockbench.BBModel;

public class BBModelElement
implements INBTable,
IReadJsonable {
    public String name;
    public boolean box_uv;
    public boolean rescale;
    public boolean locked;
    public boolean allow_mirror_modeling;
    public String render_order;
    public double[] from;
    public double[] to;
    public double[] rotation;
    public double[] origin;
    public int autouv;
    public int color;
    public int[] uv_offset;
    public String type;
    public String uuid;
    public FaceUV[] faces;
    public static final String[] FACES_ENUM = new String[]{"north", "east", "south", "west", "up", "down"};

    public BBModelElement(JsonHelper json) {
        this.readJson(json);
        BBModel.elementMap.put(this.uuid, this);
    }

    @Override
    public void readNBT(by nbt) {
        this.name = nbt.i("name");
        this.box_uv = nbt.n("box_uv");
        this.rescale = nbt.n("rescale");
        this.locked = nbt.n("locked");
        this.allow_mirror_modeling = nbt.n("allow_mirror_modeling");
        this.render_order = nbt.i("render_order");
        this.from = Tools.readDoubleArrayFromNBT(nbt, "from");
        this.to = Tools.readDoubleArrayFromNBT(nbt, "to");
        this.rotation = Tools.readDoubleArrayFromNBT(nbt, "rotation");
        this.origin = Tools.readDoubleArrayFromNBT(nbt, "origin");
        this.autouv = nbt.e("autouv");
        this.color = nbt.e("color");
        this.uv_offset = nbt.k("uv_offset");
        this.type = nbt.i("type");
        this.uuid = nbt.i("uuid");
        by facesNbt = nbt.l("faces");
        this.faces = new FaceUV[6];
        for (int i = 0; i < 6; ++i) {
            this.faces[i] = new FaceUV(facesNbt.l(FACES_ENUM[i]));
        }
    }

    @Override
    public by writeNBT() {
        by nbt = new by();
        nbt.a("name", this.name);
        nbt.a("box_uv", this.box_uv);
        nbt.a("rescale", this.rescale);
        nbt.a("locked", this.locked);
        nbt.a("allow_mirror_modeling", this.allow_mirror_modeling);
        nbt.a("render_order", this.render_order);
        Tools.writeDoubleArray2NBT(nbt, this.from, "from");
        Tools.writeDoubleArray2NBT(nbt, this.to, "to");
        nbt.a("autouv", this.autouv);
        nbt.a("color", this.color);
        Tools.writeDoubleArray2NBT(nbt, this.origin, "origin");
        Tools.writeDoubleArray2NBT(nbt, this.rotation, "rotation");
        nbt.a("uv_offset", this.uv_offset);
        nbt.a("type", this.type);
        nbt.a("uuid", this.uuid);
        by facesNbt = new by();
        for (int i = 0; i < 6; ++i) {
            facesNbt.a(FACES_ENUM[i], (cl)this.faces[i].writeNBT());
        }
        nbt.a("faces", (cl)facesNbt);
        return nbt;
    }

    @Override
    public void readJson(JsonHelper json) {
        int[] nArray;
        double[] dArray;
        this.name = json.getString("name");
        this.box_uv = json.hasKey("box_uv") && json.getBoolean("box_uv");
        this.rescale = json.hasKey("rescale") && json.getBoolean("rescale");
        this.locked = json.hasKey("locked") && json.getBoolean("locked");
        this.allow_mirror_modeling = json.hasKey("allow_mirror_modeling") && json.getBoolean("allow_mirror_modeling");
        this.render_order = json.getString("render_order");
        this.from = json.getDoubleArray("from");
        this.to = json.getDoubleArray("to");
        this.autouv = json.getInt("autouv");
        this.color = json.getInt("color");
        this.origin = json.getDoubleArray("origin");
        if (json.hasKey("rotation")) {
            dArray = json.getDoubleArray("rotation");
        } else {
            double[] dArray2 = new double[3];
            dArray2[0] = 0.0;
            dArray2[1] = 0.0;
            dArray = dArray2;
            dArray2[2] = 0.0;
        }
        this.rotation = dArray;
        if (json.hasKey("uv_offset")) {
            nArray = json.getIntArray("uv_offset");
        } else {
            int[] nArray2 = new int[2];
            nArray2[0] = 0;
            nArray = nArray2;
            nArray2[1] = 0;
        }
        this.uv_offset = nArray;
        this.type = json.getString("type");
        this.uuid = json.getString("uuid");
        JsonHelper facesJson = json.getSubJson("faces");
        this.faces = new FaceUV[6];
        for (int i = 0; i < 6; ++i) {
            this.faces[i] = new FaceUV(facesJson.getSubJson(FACES_ENUM[i]));
        }
    }

    public static class FaceUV
    implements INBTable,
    IReadJsonable {
        public int[] uv;
        public int textureId;

        public FaceUV(JsonHelper json) {
            this.readJson(json);
        }

        public FaceUV(by nbt) {
            this.readNBT(nbt);
        }

        @Override
        public void readNBT(by nbt) {
            this.uv = nbt.k("uv");
            this.textureId = nbt.e("texture");
        }

        @Override
        public by writeNBT() {
            by nbt = new by();
            nbt.a("uv", this.uv);
            nbt.a("texture", this.textureId);
            return nbt;
        }

        @Override
        public void readJson(JsonHelper json) {
            this.uv = json.getIntArray("uv");
            this.textureId = json.getInt("texture");
        }
    }
}


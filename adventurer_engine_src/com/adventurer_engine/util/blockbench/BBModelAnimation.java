/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.StringMap
 */
package com.adventurer_engine.util.blockbench;

import com.adventurer_engine.util.IReadJsonable;
import com.adventurer_engine.util.JsonHelper;
import com.adventurer_engine.util.blockbench.BBModel;
import com.google.gson.internal.StringMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BBModelAnimation
implements IReadJsonable {
    public String uuid;
    public String name;
    public String loop;
    public boolean override;
    public double length;
    public double snapping;
    public boolean selected;
    public StringMap<BBModelAnimator> animators;

    public BBModelAnimation(JsonHelper json) {
        this.readJson(json);
        BBModel.animationMap.put(this.uuid, this);
    }

    @Override
    public void readJson(JsonHelper json) {
        this.uuid = json.getString("uuid");
        this.name = json.getString("name");
        this.loop = json.getString("loop");
        this.override = json.getBoolean("override");
        this.length = json.getDouble("length");
        this.snapping = json.getDouble("snapping");
        this.selected = json.getBoolean("selected");
        JsonHelper animatorsJson = json.getSubJson("animators");
        Set<String> keys = animatorsJson.getKeySet();
        this.animators = new StringMap();
        for (String k : keys) {
            BBModelAnimator ani = new BBModelAnimator(animatorsJson.getSubJson(k));
            this.animators.put(k, (Object)ani);
        }
    }

    public static class KeyFrame
    implements IReadJsonable,
    Comparable<KeyFrame> {
        public String channel;
        public double[] data_points;
        public String uuid;
        public double time;
        public int color;
        public String interpolation;

        public KeyFrame(JsonHelper json) {
            this.readJson(json);
            this.time *= 20.0;
        }

        @Override
        public void readJson(JsonHelper json) {
            this.channel = json.getString("channel");
            this.data_points = new double[3];
            JsonHelper data = json.getJsonList("data_points").get(0);
            this.data_points[0] = data.getDouble("x");
            this.data_points[1] = data.getDouble("y");
            this.data_points[2] = data.getDouble("z");
            this.uuid = json.getString("uuid");
            this.time = json.getDouble("time");
            this.color = json.getInt("color");
            this.interpolation = json.getString("interpolation");
        }

        @Override
        public int compareTo(KeyFrame o) {
            return Double.compare(this.time, o.time);
        }
    }

    public static class BBModelAnimator
    implements IReadJsonable {
        public String name;
        public String type;
        public List<KeyFrame> positionChannel;
        public List<KeyFrame> rotationChannel;
        public List<KeyFrame> scaleChannel;

        public BBModelAnimator(JsonHelper json) {
            this.readJson(json);
        }

        @Override
        public void readJson(JsonHelper json) {
            this.name = json.getString("name");
            this.type = json.getString("type");
            List<JsonHelper> keyFrames = json.getJsonList("keyframes");
            this.positionChannel = new ArrayList<KeyFrame>();
            this.rotationChannel = new ArrayList<KeyFrame>();
            this.scaleChannel = new ArrayList<KeyFrame>();
            for (JsonHelper j : keyFrames) {
                KeyFrame k = new KeyFrame(j);
                if ("position".equals(k.channel)) {
                    this.positionChannel.add(k);
                }
                if ("rotation".equals(k.channel)) {
                    this.rotationChannel.add(k);
                }
                if (!"scale".equals(k.channel)) continue;
                this.scaleChannel.add(k);
            }
            Collections.sort(this.positionChannel);
            Collections.sort(this.rotationChannel);
            Collections.sort(this.scaleChannel);
        }
    }
}


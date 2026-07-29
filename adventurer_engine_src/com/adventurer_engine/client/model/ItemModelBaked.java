/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bbo
 *  bjo
 *  bjp
 */
package com.adventurer_engine.client.model;

import com.adventurer_engine.client.model.tmt.ModelRendererTurbo;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.JsonHelper;
import java.io.InputStreamReader;
import java.util.List;

public class ItemModelBaked
extends bbo {
    ModelRendererTurbo[] parts;
    public final bjo texture;
    public float modelScale;
    public JsonHelper thirdPerson_righthand;
    public JsonHelper firstPerson_righthand;
    public JsonHelper ground;
    private static final int otto = 16;

    public ItemModelBaked(bjo jsonFile, bjo texture) {
        this.texture = texture;
        this.modelScale = 1.0f;
        try {
            bjp s = atv.w().K();
            InputStreamReader as = new InputStreamReader(s.a(jsonFile).b());
            JsonHelper json = new JsonHelper(as);
            List texture_size = json.getList("texture_size");
            this.resolveCube(json.getJsonList("elements"), ((Double)texture_size.get(0)).intValue(), ((Double)texture_size.get(1)).intValue());
            JsonHelper display = json.getSubJson("display");
            this.thirdPerson_righthand = display.getSubJson("thirdperson_righthand");
            this.firstPerson_righthand = display.getSubJson("firstperson_righthand");
            this.ground = display.getSubJson("ground");
            json.close();
        }
        catch (Exception ignored) {
            ignored.printStackTrace(DebugGadgets.debugFile);
        }
    }

    public void renderItem(float f) {
        for (ModelRendererTurbo renderer : this.parts) {
            if (renderer == null) continue;
            renderer.a(f);
        }
    }

    private void resolveCube(List<JsonHelper> element, int textureSizeX, int textureSizeY) {
        this.parts = new ModelRendererTurbo[element.size()];
        float o0 = 0.0f;
        for (int i = 0; i < this.parts.length; ++i) {
            JsonHelper cube = element.get(i);
            List from = cube.getList("from");
            List to = cube.getList("to");
            JsonHelper faces = cube.getSubJson("faces");
            JsonHelper rotation = cube.getSubJson("rotation");
            float angle = rotation.getFloat("angle");
            String axis = rotation.getString("axis");
            List origin = rotation.getList("origin");
            JsonHelper east = faces.getSubJson("east");
            JsonHelper up = faces.getSubJson("up");
            List east_uv = east.getList("uv");
            List up_uv = up.getList("uv");
            float ox = ((Double)from.get(0)).floatValue();
            float oy = ((Double)from.get(1)).floatValue();
            float oz = ((Double)from.get(2)).floatValue();
            float px = ((Double)origin.get(0)).floatValue();
            float py = ((Double)origin.get(1)).floatValue();
            float pz = ((Double)origin.get(2)).floatValue();
            float ex = ((Double)to.get(0)).floatValue() - ox;
            float ey2 = ((Double)to.get(1)).floatValue() - oy;
            float ez = ((Double)to.get(2)).floatValue() - oz;
            int dx = (int)ex;
            int dy = (int)ey2;
            int dz = (int)ez;
            ex -= (float)dx;
            ey2 -= (float)dy;
            ez -= (float)dz;
            int tx = (int)((Double)east_uv.get(0) * (double)(textureSizeX / 16));
            int ty = (int)((Double)up_uv.get(3) * (double)(textureSizeX / 16));
            float rx = "x".equals(axis) ? (float)((double)(angle / 180.0f) * Math.PI) : o0;
            float ry = "y".equals(axis) ? (float)((double)(angle / 180.0f) * Math.PI) : o0;
            float rz = "z".equals(axis) ? (float)((double)(-(angle / 180.0f)) * Math.PI) : o0;
            this.parts[i] = new ModelRendererTurbo(this, tx, ty, textureSizeX, textureSizeY);
            this.parts[i].addShapeBox(ox -= px, oy -= py, oz -= pz, dx, dy, dz, 0.0f, -dx, -dy, o0, -ex - (float)dx, -dy, o0, -ex - (float)dx, -dy, ez, -dx, -dy, ez, -dx, -ey2 - (float)dy, o0, -ex - (float)dx, -ey2 - (float)dy, o0, -ex - (float)dx, -ey2 - (float)dy, ez, -dx, -ey2 - (float)dy, ez);
            this.parts[i].a(px, py, pz);
            this.parts[i].f = rx;
            this.parts[i].g = ry;
            this.parts[i].h = rz;
        }
    }
}


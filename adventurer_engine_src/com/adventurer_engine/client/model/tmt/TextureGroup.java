/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bgl
 *  bim
 *  bjo
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.TexturedPolygon;
import java.util.ArrayList;

public class TextureGroup {
    public ArrayList<TexturedPolygon> poly = new ArrayList();
    public String texture = "";

    public void addPoly(TexturedPolygon polygon) {
        this.poly.add(polygon);
    }

    public void loadTexture() {
        this.loadTexture(-1);
    }

    public void loadTexture(int defaultTexture) {
        if (!this.texture.equals("")) {
            bim renderengine = bgl.a.e;
            renderengine.a(new bjo("", this.texture));
        } else if (defaultTexture > -1) {
            bgl.a.e.a(new bjo("", ""));
        }
    }
}


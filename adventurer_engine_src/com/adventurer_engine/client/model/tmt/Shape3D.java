/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.PositionTransformVertex;
import com.adventurer_engine.client.model.tmt.TexturedPolygon;

public class Shape3D {
    public PositionTransformVertex[] vertices;
    public TexturedPolygon[] faces;

    public Shape3D(PositionTransformVertex[] verts, TexturedPolygon[] poly) {
        this.vertices = verts;
        this.faces = poly;
    }
}


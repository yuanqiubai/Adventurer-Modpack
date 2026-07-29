/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.PositionTextureVertex;
import com.adventurer_engine.client.model.tmt.TransformGroup;
import java.util.ArrayList;

public class PositionTransformVertex
extends PositionTextureVertex {
    public atc neutralVector;
    public ArrayList<TransformGroup> transformGroups = new ArrayList();

    public PositionTransformVertex(float x, float y, float z, float u, float v) {
        this(atc.a((double)x, (double)y, (double)z), u, v);
    }

    public PositionTransformVertex(PositionTextureVertex vertex, float u, float v) {
        super(vertex, u, v);
        this.neutralVector = vertex instanceof PositionTransformVertex ? ((PositionTransformVertex)vertex).neutralVector : atc.a((double)vertex.a.c, (double)vertex.a.d, (double)vertex.a.e);
    }

    public PositionTransformVertex(PositionTextureVertex vertex) {
        this(vertex, vertex.b, vertex.c);
    }

    public PositionTransformVertex(atc vector, float u, float v) {
        super(vector, u, v);
        this.neutralVector = atc.a((double)vector.c, (double)vector.d, (double)vector.e);
    }

    public void setTransformation() {
        int i;
        if (this.transformGroups.size() == 0) {
            this.a.c = this.neutralVector.c;
            this.a.d = this.neutralVector.d;
            this.a.e = this.neutralVector.e;
            return;
        }
        double weight = 0.0;
        for (i = 0; i < this.transformGroups.size(); ++i) {
            weight += this.transformGroups.get(i).getWeight();
        }
        this.a.c = 0.0;
        this.a.d = 0.0;
        this.a.e = 0.0;
        for (i = 0; i < this.transformGroups.size(); ++i) {
            TransformGroup group = this.transformGroups.get(i);
            double cWeight = group.getWeight() / weight;
            atc vector = group.doTransformation(this);
            this.a.c += cWeight * vector.c;
            this.a.d += cWeight * vector.d;
            this.a.e += cWeight * vector.e;
        }
    }

    public void addGroup(TransformGroup group) {
        this.transformGroups.add(group);
    }

    public void removeGroup(TransformGroup group) {
        this.transformGroups.remove(group);
    }
}


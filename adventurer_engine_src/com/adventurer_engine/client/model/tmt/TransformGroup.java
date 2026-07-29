/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.PositionTransformVertex;

public abstract class TransformGroup {
    public abstract double getWeight();

    public abstract atc doTransformation(PositionTransformVertex var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  ls
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.Angle3D;
import com.adventurer_engine.client.model.tmt.Bone;
import com.adventurer_engine.client.model.tmt.PositionTransformVertex;
import com.adventurer_engine.client.model.tmt.TransformGroup;

public class TransformGroupBone
extends TransformGroup {
    protected Angle3D baseAngles;
    protected atc baseVector;
    protected Bone attachedBone;
    protected double weight;

    public TransformGroupBone(Bone bone, double wght) {
        this.baseVector = bone.getPosition();
        this.baseAngles = bone.getAbsoluteAngle();
        this.attachedBone = bone;
        this.weight = wght;
    }

    public Angle3D getBaseAngles() {
        return this.baseAngles.copy();
    }

    public Angle3D getTransformAngle() {
        Angle3D returnAngle = this.attachedBone.getAbsoluteAngle().copy();
        returnAngle.angleX -= this.baseAngles.angleX;
        returnAngle.angleY -= this.baseAngles.angleY;
        returnAngle.angleZ -= this.baseAngles.angleZ;
        return returnAngle;
    }

    public atc getBaseVector() {
        return atc.a((double)this.baseVector.c, (double)this.baseVector.d, (double)this.baseVector.e);
    }

    public atc getTransformVector() {
        return this.baseVector.a(this.attachedBone.getPosition());
    }

    public atc getCurrentVector() {
        return this.attachedBone.getPosition();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    public void attachBone(Bone bone) {
        this.baseVector = bone.getPosition();
        this.baseAngles = bone.getAbsoluteAngle();
        this.attachedBone = bone;
    }

    @Override
    public atc doTransformation(PositionTransformVertex vertex) {
        atc vector = atc.a((double)vertex.neutralVector.c, (double)vertex.neutralVector.d, (double)vertex.neutralVector.e);
        vector = this.getBaseVector().a(vector);
        Angle3D angle = this.getTransformAngle();
        this.setVectorRotations(vector, angle.angleX, angle.angleY, angle.angleZ);
        return vector;
    }

    protected void setVectorRotations(atc vector, float xRot, float yRot, float zRot) {
        float x = xRot;
        float y = yRot;
        float z = zRot;
        float xC = ls.b((float)x);
        float xS = ls.a((float)x);
        float yC = ls.b((float)y);
        float yS = ls.a((float)y);
        float zC = ls.b((float)z);
        float zS = ls.a((float)z);
        double xVec = vector.c;
        double yVec = vector.d;
        double zVec = vector.e;
        double xy = (double)xC * yVec - (double)xS * zVec;
        double xz = (double)xC * zVec + (double)xS * yVec;
        double yz = (double)yC * xz - (double)yS * xVec;
        double yx = (double)yC * xVec + (double)yS * xz;
        double zx = (double)zC * yx - (double)zS * xy;
        double zy = (double)zC * xy + (double)zS * yx;
        xVec = zx;
        yVec = zy;
        zVec = yz;
        vector.c = xVec;
        vector.d = yVec;
        vector.e = zVec;
    }
}


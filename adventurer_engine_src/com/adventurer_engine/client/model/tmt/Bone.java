/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  bcu
 *  ls
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.Angle3D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bone {
    protected Angle3D neutralAngles;
    public Angle3D relativeAngles;
    protected Angle3D absoluteAngles;
    private atc positionVector;
    private float length;
    private Bone parentNode;
    protected ArrayList<Bone> childNodes;
    private ArrayList<bcu> models;
    private Map<bcu, Angle3D> modelBaseRot;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    public Bone(float x, float y, float z, float l) {
        this.neutralAngles = new Angle3D(x, y, z);
        this.relativeAngles = new Angle3D(0.0f, 0.0f, 0.0f);
        this.absoluteAngles = new Angle3D(0.0f, 0.0f, 0.0f);
        this.positionVector = atc.a((double)0.0, (double)0.0, (double)0.0);
        this.length = l;
        this.childNodes = new ArrayList();
        this.models = new ArrayList();
        this.modelBaseRot = new HashMap<bcu, Angle3D>();
        this.parentNode = null;
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
        this.offsetZ = 0.0f;
        this.positionVector = atc.a((double)0.0, (double)0.0, (double)0.0);
    }

    public Bone(float xOrig, float yOrig, float zOrig, float xRot, float yRot, float zRot, float l) {
        this(xRot, yRot, zRot, l);
        this.positionVector = this.setOffset(xOrig, yOrig, zOrig);
    }

    public Bone(float x, float y, float z, float l, Bone parent) {
        this(x, y, z, l);
        this.attachBone(parent);
    }

    public void detachBone() {
        this.parentNode.childNodes.remove(this);
        this.parentNode = null;
    }

    public void attachBone(Bone parent) {
        if (this.parentNode != null) {
            this.detachBone();
        }
        this.parentNode = parent;
        parent.addChildBone(this);
        this.offsetX = parent.offsetX;
        this.offsetY = parent.offsetY;
        this.offsetZ = parent.offsetZ;
        this.resetOffset();
    }

    public atc setOffset(float x, float y, float z) {
        if (this.parentNode != null) {
            atc vector = this.parentNode.setOffset(x, y, z);
            this.offsetX = (float)vector.c;
            this.offsetY = (float)vector.d;
            this.offsetZ = (float)vector.e;
            return vector;
        }
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.resetOffset(true);
        return atc.a((double)x, (double)y, (double)z);
    }

    public void resetOffset() {
        this.resetOffset(false);
    }

    public void resetOffset(boolean doRecursive) {
        if (this.parentNode != null) {
            this.positionVector = atc.a((double)0.0, (double)0.0, (double)this.parentNode.length);
            this.parentNode.setVectorRotations(this.positionVector);
            this.positionVector.c += this.parentNode.positionVector.c;
            this.positionVector.d += this.parentNode.positionVector.d;
            this.positionVector.e += this.parentNode.positionVector.e;
        }
        if (doRecursive && !this.childNodes.isEmpty()) {
            for (int index = 0; index < this.childNodes.size(); ++index) {
                this.childNodes.get(index).resetOffset(doRecursive);
            }
        }
    }

    public void setNeutralRotation(float x, float y, float z) {
        this.neutralAngles.angleX = x;
        this.neutralAngles.angleY = y;
        this.neutralAngles.angleZ = z;
    }

    public Bone getRootParent() {
        if (this.parentNode == null) {
            return this;
        }
        return this.parentNode.getRootParent();
    }

    public void addModel(bcu model) {
        this.addModel(model, false);
    }

    public void addModel(bcu model, boolean inherit) {
        this.addModel(model, 0.0f, 0.0f, 0.0f, inherit);
    }

    public void addModel(bcu model, boolean inherit, boolean isUpright) {
        this.addModel(model, 0.0f, 0.0f, 0.0f, inherit, isUpright);
    }

    public void addModel(bcu model, float x, float y, float z) {
        this.addModel(model, x, y, z, false);
    }

    public void addModel(bcu model, float x, float y, float z, boolean inherit) {
        this.addModel(model, x, y, z, inherit, false);
    }

    public void addModel(bcu model, float x, float y, float z, boolean inherit, boolean isUpright) {
        if (inherit) {
            x += this.neutralAngles.angleX + (isUpright ? 1.5707964f : 0.0f);
            y += this.neutralAngles.angleY;
            z += this.neutralAngles.angleZ;
        }
        this.models.add(model);
        this.modelBaseRot.put(model, new Angle3D(x, y, z));
    }

    public void removeModel(bcu model) {
        this.models.remove(model);
        this.modelBaseRot.remove(model);
    }

    public Angle3D getAbsoluteAngle() {
        return new Angle3D(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
    }

    public atc getPosition() {
        return atc.a((double)this.positionVector.c, (double)this.positionVector.d, (double)this.positionVector.e);
    }

    protected void addChildBone(Bone bone) {
        this.childNodes.add(bone);
    }

    public void prepareDraw() {
        if (this.parentNode != null) {
            this.parentNode.prepareDraw();
        } else {
            this.setAbsoluteRotations();
            this.setVectors();
        }
    }

    public void setRotations(float x, float y, float z) {
        this.relativeAngles.angleX = x;
        this.relativeAngles.angleY = y;
        this.relativeAngles.angleZ = z;
    }

    protected void setAbsoluteRotations() {
        this.absoluteAngles.angleX = this.relativeAngles.angleX;
        this.absoluteAngles.angleY = this.relativeAngles.angleY;
        this.absoluteAngles.angleZ = this.relativeAngles.angleZ;
        for (int i = 0; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setAbsoluteRotations(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
        }
    }

    protected void setAbsoluteRotations(float x, float y, float z) {
        this.absoluteAngles.angleX = this.relativeAngles.angleX + x;
        this.absoluteAngles.angleY = this.relativeAngles.angleY + y;
        this.absoluteAngles.angleZ = this.relativeAngles.angleZ + z;
        for (int i = 0; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setAbsoluteRotations(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
        }
    }

    protected void setVectorRotations(atc vector) {
        float x = this.neutralAngles.angleX + this.absoluteAngles.angleX;
        float y = this.neutralAngles.angleY + this.absoluteAngles.angleY;
        float z = this.neutralAngles.angleZ + this.absoluteAngles.angleZ;
        this.setVectorRotations(vector, x, y, z);
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

    protected void addVector(atc destVec, atc srcVec) {
        destVec.c += srcVec.c;
        destVec.d += srcVec.d;
        destVec.e += srcVec.e;
    }

    protected void setVectors() {
        atc tempVec = atc.a((double)0.0, (double)0.0, (double)this.length);
        this.positionVector = atc.a((double)this.offsetX, (double)this.offsetY, (double)this.offsetZ);
        this.addVector(tempVec, this.positionVector);
        this.setVectorRotations(tempVec);
        for (int i = 0; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setVectors(tempVec);
        }
    }

    protected void setVectors(atc vector) {
        this.positionVector = vector;
        atc tempVec = atc.a((double)0.0, (double)0.0, (double)this.length);
        this.setVectorRotations(tempVec);
        this.addVector(tempVec, vector);
        for (int i = 0; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setVectors(tempVec);
        }
    }

    public void setAnglesToModels() {
        int i;
        for (i = 0; i < this.models.size(); ++i) {
            bcu currentModel = this.models.get(i);
            Angle3D baseAngles = this.modelBaseRot.get(currentModel);
            currentModel.f = baseAngles.angleX + this.absoluteAngles.angleX;
            currentModel.g = baseAngles.angleY + this.absoluteAngles.angleY;
            currentModel.h = baseAngles.angleZ + this.absoluteAngles.angleZ;
            currentModel.c = (float)this.positionVector.c;
            currentModel.d = (float)this.positionVector.d;
            currentModel.e = (float)this.positionVector.e;
        }
        for (i = 0; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setAnglesToModels();
        }
    }
}


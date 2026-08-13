/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 */
package noppes.client.model.part;

import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelScaleRenderer;

public class ModelHeadwear
extends ModelScaleRenderer {
    public ModelHeadwear(bbo base) {
        super(base);
        Model2DRenderer right = new Model2DRenderer(base, 32.0f, 8.0f, 8, 8, 64.0f, 32.0f);
        right.a(-4.64f, 0.8f, 4.64f);
        right.setScale(0.58f);
        right.setThickness(0.65f);
        this.setRotation(right, 0.0f, 1.5707964f, 0.0f);
        this.a(right);
        Model2DRenderer left = new Model2DRenderer(base, 48.0f, 8.0f, 8, 8, 64.0f, 32.0f);
        left.a(4.64f, 0.8f, -4.64f);
        left.setScale(0.58f);
        left.setThickness(0.65f);
        this.setRotation(left, 0.0f, -1.5707964f, 0.0f);
        this.a(left);
        Model2DRenderer front = new Model2DRenderer(base, 40.0f, 8.0f, 8, 8, 64.0f, 32.0f);
        front.a(-4.64f, 0.8f, -4.64f);
        front.setScale(0.58f);
        front.setThickness(0.65f);
        this.setRotation(front, 0.0f, 0.0f, 0.0f);
        this.a(front);
        Model2DRenderer back = new Model2DRenderer(base, 56.0f, 8.0f, 8, 8, 64.0f, 32.0f);
        back.a(4.64f, 0.8f, 4.64f);
        back.setScale(0.58f);
        back.setThickness(0.65f);
        this.setRotation(back, 0.0f, (float)Math.PI, 0.0f);
        this.a(back);
        Model2DRenderer top = new Model2DRenderer(base, 40.0f, 0.0f, 8, 8, 64.0f, 32.0f);
        top.a(-4.64f, -8.5f, -4.64f);
        top.setScale(0.58f);
        top.setThickness(0.65f);
        this.setRotation(top, -1.5707964f, 0.0f, 0.0f);
        this.a(top);
        Model2DRenderer bottom = new Model2DRenderer(base, 48.0f, 0.0f, 8, 8, 64.0f, 32.0f);
        bottom.a(-4.64f, 0.0f, -4.64f);
        bottom.setScale(0.58f);
        bottom.setThickness(0.65f);
        this.setRotation(bottom, -1.5707964f, 0.0f, 0.0f);
        this.a(bottom);
    }

    @Override
    public void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }
}


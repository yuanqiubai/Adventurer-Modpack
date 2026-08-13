/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  ls
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.part.legs;

import noppes.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNagaLegs
extends bcu {
    public boolean isRiding = false;
    public boolean isSneaking = false;
    public boolean isSleeping = false;
    public boolean isCrawling = false;
    private final bcu nagaPart1;
    private final bcu nagaPart2;
    private final bcu nagaPart3;
    private final bcu nagaPart4;
    private final bcu nagaPart5;

    public ModelNagaLegs(bbo base) {
        super(base);
        this.nagaPart1 = new bcu(base, 0, 0);
        bcu legPart = new bcu(base, 0, 16);
        legPart.a(0.0f, -2.0f, -2.0f, 4, 4, 4);
        legPart.a(-4.0f, 0.0f, 0.0f);
        this.nagaPart1.a(legPart);
        legPart = new bcu(base, 0, 16);
        legPart.i = true;
        legPart.a(0.0f, -2.0f, -2.0f, 4, 4, 4);
        this.nagaPart1.a(legPart);
        this.nagaPart2 = new bcu(base, 0, 0);
        this.nagaPart2.m = this.nagaPart1.m;
        this.nagaPart3 = new bcu(base, 0, 0);
        ModelPlaneRenderer plane = new ModelPlaneRenderer(base, 4, 24);
        plane.addBackPlane(0.0f, -2.0f, 0.0f, 4, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 4, 24);
        plane.i = true;
        plane.addBackPlane(0.0f, -2.0f, 0.0f, 4, 4);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 8, 24);
        plane.addBackPlane(0.0f, -2.0f, 6.0f, 4, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 8, 24);
        plane.i = true;
        plane.addBackPlane(0.0f, -2.0f, 6.0f, 4, 4);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 4, 26);
        plane.addTopPlane(0.0f, -2.0f, -6.0f, 4, 6);
        plane.a(-4.0f, 0.0f, 0.0f);
        plane.f = (float)Math.PI;
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 4, 26);
        plane.i = true;
        plane.addTopPlane(0.0f, -2.0f, -6.0f, 4, 6);
        plane.f = (float)Math.PI;
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 8, 26);
        plane.addTopPlane(0.0f, -2.0f, 0.0f, 4, 6);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 8, 26);
        plane.i = true;
        plane.addTopPlane(0.0f, -2.0f, 0.0f, 4, 6);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 0, 26);
        plane.f = 1.5707964f;
        plane.addSidePlane(0.0f, 0.0f, -2.0f, 6, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.nagaPart3.a((bcu)plane);
        plane = new ModelPlaneRenderer(base, 0, 26);
        plane.f = 1.5707964f;
        plane.addSidePlane(4.0f, 0.0f, -2.0f, 6, 4);
        this.nagaPart3.a((bcu)plane);
        this.nagaPart4 = new bcu(base, 0, 0);
        this.nagaPart4.m = this.nagaPart3.m;
        this.nagaPart5 = new bcu(base, 0, 0);
        legPart = new bcu(base, 56, 20);
        legPart.a(0.0f, 0.0f, -2.0f, 2, 5, 2);
        legPart.a(-2.0f, 0.0f, 0.0f);
        legPart.f = 1.5707964f;
        this.nagaPart5.a(legPart);
        legPart = new bcu(base, 56, 20);
        legPart.i = true;
        legPart.a(0.0f, 0.0f, -2.0f, 2, 5, 2);
        legPart.f = 1.5707964f;
        this.nagaPart5.a(legPart);
        this.a(this.nagaPart1);
        this.a(this.nagaPart2);
        this.a(this.nagaPart3);
        this.a(this.nagaPart4);
        this.a(this.nagaPart5);
        this.nagaPart1.a(0.0f, 14.0f, 0.0f);
        this.nagaPart2.a(0.0f, 18.0f, 0.6f);
        this.nagaPart3.a(0.0f, 22.0f, -0.3f);
        this.nagaPart4.a(0.0f, 22.0f, 5.0f);
        this.nagaPart5.a(0.0f, 22.0f, 10.0f);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        this.nagaPart1.g = ls.b((float)(par1 * 0.6662f)) * 0.26f * par2;
        this.nagaPart2.g = ls.b((float)(par1 * 0.6662f)) * 0.5f * par2;
        this.nagaPart3.g = ls.b((float)(par1 * 0.6662f)) * 0.26f * par2;
        this.nagaPart4.g = -ls.b((float)(par1 * 0.6662f)) * 0.16f * par2;
        this.nagaPart5.g = -ls.b((float)(par1 * 0.6662f)) * 0.3f * par2;
        this.nagaPart1.a(0.0f, 14.0f, 0.0f);
        this.nagaPart2.a(0.0f, 18.0f, 0.6f);
        this.nagaPart3.a(0.0f, 22.0f, -0.3f);
        this.nagaPart4.a(0.0f, 22.0f, 5.0f);
        this.nagaPart5.a(0.0f, 22.0f, 10.0f);
        this.nagaPart1.f = 0.0f;
        this.nagaPart2.f = 0.0f;
        this.nagaPart3.f = 0.0f;
        this.nagaPart4.f = 0.0f;
        this.nagaPart5.f = 0.0f;
        if (this.isSleeping || this.isCrawling) {
            this.nagaPart3.f = -1.5707964f;
            this.nagaPart4.f = -1.5707964f;
            this.nagaPart5.f = -1.5707964f;
            this.nagaPart3.d -= 2.0f;
            this.nagaPart3.e = 0.9f;
            this.nagaPart4.d += 4.0f;
            this.nagaPart4.e = 0.9f;
            this.nagaPart5.d += 7.0f;
            this.nagaPart5.e = 2.9f;
        }
        if (this.isRiding) {
            this.nagaPart1.d -= 1.0f;
            this.nagaPart1.f = -0.19634955f;
            this.nagaPart1.e = -1.0f;
            this.nagaPart2.d -= 4.0f;
            this.nagaPart2.e = -1.0f;
            this.nagaPart3.d -= 9.0f;
            this.nagaPart3.e -= 1.0f;
            this.nagaPart4.d -= 13.0f;
            this.nagaPart4.e -= 1.0f;
            this.nagaPart5.d -= 9.0f;
            this.nagaPart5.e -= 1.0f;
            if (this.isSneaking) {
                this.nagaPart1.e += 5.0f;
                this.nagaPart3.e += 5.0f;
                this.nagaPart4.e += 5.0f;
                this.nagaPart5.e += 4.0f;
                this.nagaPart1.d -= 1.0f;
                this.nagaPart2.d -= 1.0f;
                this.nagaPart3.d -= 1.0f;
                this.nagaPart4.d -= 1.0f;
                this.nagaPart5.d -= 1.0f;
            }
        } else if (this.isSneaking) {
            this.nagaPart1.d -= 1.0f;
            this.nagaPart2.d -= 1.0f;
            this.nagaPart3.d -= 1.0f;
            this.nagaPart4.d -= 1.0f;
            this.nagaPart5.d -= 1.0f;
            this.nagaPart1.e = 5.0f;
            this.nagaPart2.e = 3.0f;
        }
    }

    public void a(float par7) {
        if (!this.k && this.j) {
            this.nagaPart1.a(par7);
            this.nagaPart3.a(par7);
            if (!this.isRiding) {
                this.nagaPart2.a(par7);
            }
            GL11.glPushMatrix();
            GL11.glScalef((float)0.74f, (float)0.7f, (float)0.85f);
            GL11.glTranslatef((float)this.nagaPart3.g, (float)0.66f, (float)0.06f);
            this.nagaPart4.a(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.nagaPart3.g + this.nagaPart4.g), (float)0.0f, (float)0.0f);
            this.nagaPart5.a(par7);
            GL11.glPopMatrix();
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model.part.tails;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.ModelPlaneRenderer;

public class ModelDragonTail
extends bcu {
    public ModelDragonTail(ModelMPM base) {
        super((bbo)base);
        int x = 52;
        int y = 16;
        bcu dragon = new bcu((bbo)base, x, y);
        dragon.a(0.0f, 0.0f, 3.0f);
        this.a(dragon);
        bcu DragonTail2 = new bcu((bbo)base, x, y);
        DragonTail2.a(0.0f, 2.0f, 2.0f);
        bcu DragonTail3 = new bcu((bbo)base, x, y);
        DragonTail3.a(0.0f, 4.5f, 4.0f);
        bcu DragonTail4 = new bcu((bbo)base, x, y);
        DragonTail4.a(0.0f, 7.0f, 5.75f);
        bcu DragonTail5 = new bcu((bbo)base, x, y);
        DragonTail5.a(0.0f, 9.0f, 8.0f);
        ModelPlaneRenderer planeLeft = new ModelPlaneRenderer(base, x, y);
        planeLeft.addSidePlane(-1.5f, -1.5f, -1.5f, 3, 3);
        ModelPlaneRenderer planeRight = new ModelPlaneRenderer(base, x, y);
        planeRight.addSidePlane(-1.5f, -1.5f, -1.5f, 3, 3);
        this.setRotation(planeRight, (float)Math.PI, (float)Math.PI, 0.0f);
        ModelPlaneRenderer planeTop = new ModelPlaneRenderer(base, x, y);
        planeTop.addTopPlane(-1.5f, -1.5f, -1.5f, 3, 3);
        this.setRotation(planeTop, 0.0f, -1.5707964f, 0.0f);
        ModelPlaneRenderer planeBottom = new ModelPlaneRenderer(base, x, y);
        planeBottom.addTopPlane(-1.5f, -1.5f, -1.5f, 3, 3);
        this.setRotation(planeBottom, 0.0f, -1.5707964f, (float)Math.PI);
        ModelPlaneRenderer planeBack = new ModelPlaneRenderer(base, x, y);
        planeBack.addBackPlane(-1.5f, -1.5f, -1.5f, 3, 3);
        this.setRotation(planeBack, 0.0f, 0.0f, 1.5707964f);
        ModelPlaneRenderer planeFront = new ModelPlaneRenderer(base, x, y);
        planeFront.addBackPlane(-1.5f, -1.5f, -1.5f, 3, 3);
        this.setRotation(planeFront, 0.0f, (float)Math.PI, -1.5707964f);
        dragon.a((bcu)planeLeft);
        dragon.a((bcu)planeRight);
        dragon.a((bcu)planeTop);
        dragon.a((bcu)planeBottom);
        dragon.a((bcu)planeFront);
        dragon.a((bcu)planeBack);
        DragonTail2.a((bcu)planeLeft);
        DragonTail2.a((bcu)planeRight);
        DragonTail2.a((bcu)planeTop);
        DragonTail2.a((bcu)planeBottom);
        DragonTail2.a((bcu)planeFront);
        DragonTail2.a((bcu)planeBack);
        DragonTail3.a((bcu)planeLeft);
        DragonTail3.a((bcu)planeRight);
        DragonTail3.a((bcu)planeTop);
        DragonTail3.a((bcu)planeBottom);
        DragonTail3.a((bcu)planeFront);
        DragonTail3.a((bcu)planeBack);
        DragonTail4.a((bcu)planeLeft);
        DragonTail4.a((bcu)planeRight);
        DragonTail4.a((bcu)planeTop);
        DragonTail4.a((bcu)planeBottom);
        DragonTail4.a((bcu)planeFront);
        DragonTail4.a((bcu)planeBack);
        dragon.a(DragonTail2);
        dragon.a(DragonTail3);
        dragon.a(DragonTail4);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
    }

    private void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }
}


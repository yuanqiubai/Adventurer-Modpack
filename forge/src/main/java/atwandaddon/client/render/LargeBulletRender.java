package atwandaddon.client.render;

import com.adventurer_engine.client.entity.render.BulletRender;
import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;
import org.lwjgl.opengl.GL11;

public class LargeBulletRender extends BulletRender {

    @Override
    public void renderBullet(EntityBulletGeneral _bullet, double _x, double _y, double _z, float _yaw, float _pitch) {
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        super.renderBullet(_bullet, _x / 1.5D, _y / 1.5D, _z / 1.5D, _yaw, _pitch);
        GL11.glPopMatrix();
    }
}

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.model.rc;

import com.adventurer_engine.client.model.rc.EnumKeyFrameType;
import com.adventurer_engine.common.entity.neo_npc.AnimationController;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.util.blockbench.BBModel;
import com.adventurer_engine.util.blockbench.BBModelAnimation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class RCsAnimationManager
extends AnimationController {
    public int tick = 0;
    public double maxTickTime;
    public String currentAnimation;
    public BBModel currentModel;
    private boolean isPlaying = false;

    protected boolean startAnimation() {
        if (!this.currentModel.animations.containsKey((Object)this.currentAnimation)) {
            return false;
        }
        this.tick = 0;
        this.isPlaying = true;
        this.maxTickTime = ((BBModelAnimation)this.currentModel.animations.get((Object)this.currentAnimation)).length * 20.0;
        return true;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public boolean launchAnimation(String animationName, EntityRCsNPCBase npc) {
        if (animationName.equals(this.currentAnimation)) {
            this.startAnimation();
            return true;
        }
        if (!npc.model.animations.containsKey((Object)animationName)) {
            return false;
        }
        this.currentAnimation = animationName;
        this.currentModel = npc.model;
        return this.startAnimation();
    }

    @Override
    public void onUpdate() {
        if (!this.isPlaying) {
            return;
        }
        ++this.tick;
        if ((double)this.tick >= Math.ceil(this.maxTickTime)) {
            this.tick = 0;
        }
    }

    public void doRender(String boneUUID, float partialTicks, float magic) {
        if (!this.isPlaying) {
            return;
        }
        BBModelAnimation.BBModelAnimator a = (BBModelAnimation.BBModelAnimator)((BBModelAnimation)this.currentModel.animations.get((Object)this.currentAnimation)).animators.get((Object)boneUUID);
        if (a == null) {
            return;
        }
        this.handleKeyFrameList(a.positionChannel, partialTicks, magic, EnumKeyFrameType.POSITION);
        this.handleKeyFrameList(a.rotationChannel, partialTicks, magic, EnumKeyFrameType.ROTATION);
        this.handleKeyFrameList(a.scaleChannel, partialTicks, magic, EnumKeyFrameType.SCALE);
    }

    private void handleKeyFrameList(List<BBModelAnimation.KeyFrame> frames, float partialTicks, float magic, EnumKeyFrameType type) {
        if (frames.isEmpty()) {
            return;
        }
        double currentTick = this.tick;
        currentTick += (double)partialTicks;
        int startFrame = 0;
        int endFrame = 0;
        if (currentTick >= frames.get((int)(frames.size() - 1)).time) {
            startFrame = frames.size() - 1;
            endFrame = frames.size() - 1;
        } else if (currentTick > frames.get((int)0).time) {
            for (int i = 1; i < frames.size(); ++i) {
                double startTime = frames.get((int)(i - 1)).time;
                double endTime = frames.get((int)i).time;
                if (!(currentTick >= startTime) || !(currentTick <= endTime)) continue;
                startFrame = i - 1;
                endFrame = i;
                break;
            }
        }
        if (startFrame == endFrame) {
            BBModelAnimation.KeyFrame frame = frames.get(startFrame);
            this.applyFrame(frame, frame.data_points, magic, type);
        } else {
            double[] data_points = new double[3];
            double[] start_points = frames.get((int)startFrame).data_points;
            double[] end_points = frames.get((int)endFrame).data_points;
            double startTime = frames.get((int)startFrame).time;
            double endTime = frames.get((int)endFrame).time;
            for (int i = 0; i < 3; ++i) {
                double a = start_points[i];
                double b = end_points[i];
                double t0 = startTime;
                double t1 = endTime;
                double t = currentTick;
                data_points[i] = ((a - b) * t + t0 * b - t1 * a) / (t0 - t1);
            }
            this.applyFrame(frames.get(endFrame), data_points, magic, type);
        }
    }

    private void applyFrame(BBModelAnimation.KeyFrame frame, double[] data_points, float magic, EnumKeyFrameType type) {
        switch (type) {
            case SCALE: {
                GL11.glScalef((float)((float)data_points[0]), (float)((float)data_points[1]), (float)((float)data_points[2]));
                break;
            }
            case ROTATION: {
                if (data_points[2] != 0.0) {
                    GL11.glRotatef((float)((float)data_points[2]), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (data_points[1] != 0.0) {
                    GL11.glRotatef((float)((float)(-data_points[1])), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (data_points[0] == 0.0) break;
                GL11.glRotatef((float)((float)(-data_points[0])), (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case POSITION: {
                GL11.glTranslatef((float)(-((float)data_points[0]) * magic), (float)((float)data_points[1] * magic), (float)((float)data_points[2] * magic));
            }
        }
    }
}


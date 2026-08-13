/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumMovingType;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIAnimation
extends ps {
    private final EntityNPCInterface npc;
    private boolean isAttacking = false;
    private boolean isDead = false;
    private boolean isAtStartpoint = false;
    private boolean hasPath = false;

    public EntityAIAnimation(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean a() {
        this.isDead = this.npc.isKilled();
        if (this.isDead) {
            return this.npc.currentAnimation != EnumAnimation.LYING;
        }
        if (this.npc.ai.animationType == EnumAnimation.NONE) {
            return this.npc.currentAnimation != EnumAnimation.NONE;
        }
        this.isAttacking = this.npc.isAttacking();
        this.isAtStartpoint = this.npc.isVeryNearAssignedPlace();
        this.hasPath = !this.npc.k().g();
        boolean hasCorrectAnimation = this.npc.currentAnimation == this.npc.ai.animationType;
        return (this.npc.ai.movingType == EnumMovingType.Standing && this.hasNavigation()) == hasCorrectAnimation;
    }

    public void c() {
        EnumAnimation type = this.npc.ai.animationType;
        if (!this.isDead && !this.npc.isSleeping()) {
            if (this.npc.ai.movingType == EnumMovingType.Standing && this.hasNavigation() && (this.npc.ai.animationType == EnumAnimation.SITTING || this.npc.ai.animationType == EnumAnimation.LYING)) {
                type = EnumAnimation.NONE;
            }
        } else {
            type = EnumAnimation.LYING;
        }
        this.setAnimation(type);
    }

    private void setAnimation(EnumAnimation animation) {
        this.npc.currentAnimation = animation;
        this.npc.v().b(14, (Object)animation.ordinal());
        this.npc.updateHitbox();
        this.npc.b(this.npc.u, this.npc.v, this.npc.w);
    }

    private boolean hasNavigation() {
        return this.isAttacking || !this.isAtStartpoint || this.hasPath;
    }
}


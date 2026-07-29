/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.task;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.task.TaskBase;
import com.adventurer_engine.util.blockbench.BBModelAnimation;

public class TaskAnimator
extends TaskBase {
    private final String animationName;
    private final int duration;
    public static final String INTERNAL_NAME = "animator";

    @Override
    public NodeStatus onTick() {
        if (this.ai.getHost() == null) {
            return NodeStatus.FAILURE;
        }
        if (this.tick == this.duration) {
            return NodeStatus.SUCCESS;
        }
        if (this.tick > 0) {
            return NodeStatus.RUNNING;
        }
        if (this.ai.getHost().setAnimation(this.animationName)) {
            return NodeStatus.RUNNING;
        }
        return NodeStatus.FAILURE;
    }

    public TaskAnimator(String animationName, AIObject ai) {
        super(ai);
        this.animationName = animationName;
        this.duration = (int)(((BBModelAnimation)ai.getHost().model.animations.get((Object)animationName)).length * 20.0);
    }

    @Override
    public void reset() {
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        TaskAnimator copy = new TaskAnimator(this.animationName, ai);
        copy.ai = ai;
        return copy;
    }

    @Override
    public void customInit() {
    }
}


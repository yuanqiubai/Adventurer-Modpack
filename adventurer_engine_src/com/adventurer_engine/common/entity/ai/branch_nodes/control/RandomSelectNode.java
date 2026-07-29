/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.control;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.ControlNode;
import com.adventurer_engine.util.Tools;

public class RandomSelectNode
extends ControlNode {
    public static final String INTERNAL_NAME = "random_select";
    public int[] weight;
    private int weightSum = 0;

    public RandomSelectNode(AIObject ai) {
        super(ai);
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        RandomSelectNode node = new RandomSelectNode(ai);
        node.child = this.copyChild(ai);
        node.weight = this.weight;
        node.weightSum = this.weightSum;
        return node;
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        return NodeStatus.SUCCESS;
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        return NodeStatus.FAILURE;
    }

    @Override
    public NodeStatus onParentSendInit() {
        if (this.child.isEmpty()) {
            this.getParent().actionTick(NodeStatus.SUCCESS);
            return NodeStatus.SUCCESS;
        }
        int idx = this.randomWeightInteger(this.weight, this.weightSum);
        if (idx == -1) {
            this.getParent().actionTick(NodeStatus.FAILURE);
            return NodeStatus.FAILURE;
        }
        return this.executeChildNode(idx);
    }

    private int randomWeightInteger(int[] weight, int max) {
        if (weight.length == 0) {
            return -1;
        }
        if (this.weightSum == 0) {
            for (int i : weight) {
                this.weightSum += i;
            }
        }
        if (this.weightSum == 0) {
            return -1;
        }
        int r = Tools.random.nextInt(this.weightSum);
        int cur = 0;
        for (int i = 0; i < weight.length; ++i) {
            if (r >= (cur += weight[i])) continue;
            return i;
        }
        return -1;
    }
}


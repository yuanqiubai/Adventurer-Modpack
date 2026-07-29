/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.control;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.ControlNode;

public class ParallelNode
extends ControlNode {
    private int finishAmount = 0;
    private int successAmount = 0;
    public static final String INTERNAL_NAME = "parallel";

    public ParallelNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        ++this.finishAmount;
        ++this.successAmount;
        if (this.finishAmount == this.child.size()) {
            return this.onAllFinished();
        }
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        ++this.finishAmount;
        if (this.finishAmount == this.child.size()) {
            return this.onAllFinished();
        }
        return null;
    }

    private NodeStatus onAllFinished() {
        if (this.successAmount == this.child.size()) {
            return NodeStatus.SUCCESS;
        }
        return NodeStatus.FAILURE;
    }

    @Override
    public NodeStatus onParentSendInit() {
        if (this.child.isEmpty()) {
            return NodeStatus.SUCCESS;
        }
        for (int i = 0; i < this.child.size(); ++i) {
            this.executeChildNode(i);
        }
        return NodeStatus.RUNNING;
    }

    @Override
    public void reset() {
        super.reset();
        this.finishAmount = 0;
        this.successAmount = 0;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        ParallelNode node = new ParallelNode(ai);
        node.child = this.copyChild(ai);
        return node;
    }
}


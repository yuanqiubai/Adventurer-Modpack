/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.control;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.ControlNode;

public class RecursiveNode
extends ControlNode {
    private int currentIdx = 0;
    public static final String INTERNAL_NAME = "recursive";

    public RecursiveNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        this.currentIdx = (this.currentIdx + 1) % this.child.size();
        return this.executeChildNode(this.currentIdx);
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        this.currentIdx = (this.currentIdx + 1) % this.child.size();
        return this.executeChildNode(this.currentIdx);
    }

    @Override
    public NodeStatus onParentSendInit() {
        if (this.child.isEmpty()) {
            throw new RuntimeException("Recursive Node must has at least one child");
        }
        this.currentIdx = 0;
        return this.executeChildNode(this.currentIdx);
    }

    @Override
    public void reset() {
        super.reset();
        this.currentIdx = 0;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        RecursiveNode node = new RecursiveNode(ai);
        node.child = this.copyChild(ai);
        return node;
    }
}


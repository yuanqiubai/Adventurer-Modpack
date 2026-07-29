/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.decorator;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.DecoratorNode;

public class RetryNode
extends DecoratorNode {
    private int maxRetryTimes;
    private int currentTryTimes = 0;
    public static final String INTERNAL_NAME = "retry";

    public RetryNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        this.reportParent(NodeStatus.SUCCESS);
        return NodeStatus.SUCCESS;
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        if (this.currentTryTimes < this.maxRetryTimes) {
            ++this.currentTryTimes;
            this.executeChild();
            return NodeStatus.RUNNING;
        }
        this.reportParent(NodeStatus.FAILURE);
        return NodeStatus.FAILURE;
    }

    @Override
    public NodeStatus onParentSendInit() {
        this.currentTryTimes = 0;
        this.executeChild();
        return NodeStatus.RUNNING;
    }

    @Override
    public void reset() {
        super.reset();
        this.currentTryTimes = 0;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        RetryNode node = new RetryNode(ai);
        node.child = this.child.copy(ai);
        node.maxRetryTimes = this.maxRetryTimes;
        return node;
    }

    @Override
    public by writeNBT() {
        by nbt = super.writeNBT();
        nbt.a("max", this.maxRetryTimes);
        return nbt;
    }

    @Override
    public void readNBT(by nbt) {
        super.readNBT(nbt);
        this.maxRetryTimes = nbt.e("max");
    }
}


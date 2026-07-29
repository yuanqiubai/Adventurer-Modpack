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

public class ForceRepeatNode
extends DecoratorNode {
    private int repeatCount;
    private int currentCount = 0;
    public static final String INTERNAL_NAME = "forceRepeat";

    public ForceRepeatNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        return this.forceRepeat();
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        return this.forceRepeat();
    }

    private NodeStatus forceRepeat() {
        ++this.currentCount;
        if (this.currentCount >= this.repeatCount) {
            this.reportParent(NodeStatus.SUCCESS);
            return NodeStatus.SUCCESS;
        }
        this.executeChild();
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onParentSendInit() {
        this.currentCount = 0;
        this.executeChild();
        return NodeStatus.RUNNING;
    }

    @Override
    public void reset() {
        super.reset();
        this.currentCount = 0;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        ForceRepeatNode node = new ForceRepeatNode(ai);
        node.repeatCount = this.repeatCount;
        node.child = this.child.copy(ai);
        return node;
    }

    @Override
    public by writeNBT() {
        by nbt = super.writeNBT();
        nbt.a("max", this.repeatCount);
        return nbt;
    }

    @Override
    public void readNBT(by nbt) {
        super.readNBT(nbt);
        this.repeatCount = nbt.e("max");
    }
}


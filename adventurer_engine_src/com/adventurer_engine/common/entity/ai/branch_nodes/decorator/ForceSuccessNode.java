/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.decorator;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.DecoratorNode;

public class ForceSuccessNode
extends DecoratorNode {
    public static final String INTERNAL_NAME = "forceSuccess";

    public ForceSuccessNode(AIObject ai) {
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
        this.reportParent(NodeStatus.SUCCESS);
        return NodeStatus.SUCCESS;
    }

    @Override
    public NodeStatus onParentSendInit() {
        if (this.child == null) {
            return NodeStatus.SUCCESS;
        }
        this.executeChild();
        return NodeStatus.RUNNING;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        ForceSuccessNode node = new ForceSuccessNode(ai);
        node.child = this.child.copy(ai);
        return node;
    }
}


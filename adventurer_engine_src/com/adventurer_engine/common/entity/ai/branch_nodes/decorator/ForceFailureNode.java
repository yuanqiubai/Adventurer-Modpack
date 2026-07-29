/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.decorator;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.DecoratorNode;

public class ForceFailureNode
extends DecoratorNode {
    public static final String INTERNAL_NAME = "forceFailure";

    public ForceFailureNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        this.reportParent(NodeStatus.FAILURE);
        return NodeStatus.FAILURE;
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        this.reportParent(NodeStatus.FAILURE);
        return NodeStatus.FAILURE;
    }

    @Override
    public NodeStatus onParentSendInit() {
        this.executeChild();
        return NodeStatus.RUNNING;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        ForceFailureNode node = new ForceFailureNode(ai);
        node.child = this.child.copy(ai);
        return node;
    }
}


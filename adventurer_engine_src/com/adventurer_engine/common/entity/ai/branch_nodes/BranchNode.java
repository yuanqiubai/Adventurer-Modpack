/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;

public abstract class BranchNode
extends NodeBase {
    public BranchNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus actionTick(NodeStatus status) {
        switch (status) {
            case RUNNING: {
                return this.onChildReportRUNNING();
            }
            case SUCCESS: {
                return this.onChildReportSUCCESS();
            }
            case FAILURE: {
                return this.onChildReportFAILURE();
            }
            case INIT: {
                return this.onParentSendInit();
            }
        }
        return NodeStatus.FAILURE;
    }

    public abstract NodeStatus onChildReportRUNNING();

    public abstract NodeStatus onChildReportSUCCESS();

    public abstract NodeStatus onChildReportFAILURE();

    public abstract NodeStatus onParentSendInit();
}


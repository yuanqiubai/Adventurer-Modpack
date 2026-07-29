/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package com.adventurer_engine.common.entity.ai.branch_nodes;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeFactory;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.BranchNode;
import com.adventurer_engine.common.entity.ai.task.TaskBase;
import java.util.LinkedList;
import java.util.List;

public abstract class ControlNode
extends BranchNode {
    protected List<NodeBase> child;

    public ControlNode(AIObject ai) {
        super(ai);
    }

    @Override
    public void reset() {
        for (NodeBase node : this.child) {
            node.reset();
        }
    }

    protected NodeStatus executeChildNode(int idx) {
        NodeBase childNode = this.child.get(idx);
        if (childNode instanceof TaskBase) {
            this.ai.addTask((TaskBase)childNode);
        } else {
            childNode.actionTick(NodeStatus.INIT);
        }
        return NodeStatus.RUNNING;
    }

    @Override
    public by writeNBT() {
        by nbt = new by();
        cg ls2 = new cg();
        for (NodeBase node : this.child) {
            ls2.a((cl)node.writeNBT());
        }
        nbt.a("type", this.internalName());
        nbt.a("child", (cl)ls2);
        return nbt;
    }

    @Override
    public void readNBT(by nbt) {
        assert (nbt.i("type").equals(this.internalName()));
        cg ls2 = nbt.m("child");
        for (int i = 0; i < ls2.c(); ++i) {
            by childNBT = (by)ls2.b(i);
            String childType = childNBT.i("type");
            this.child.add(NodeFactory.createNode(childType, childNBT));
        }
    }

    protected List<NodeBase> copyChild(AIObject ai) {
        LinkedList<NodeBase> copy = new LinkedList<NodeBase>();
        for (NodeBase node : this.child) {
            copy.add(node.copy(ai));
        }
        return copy;
    }

    public void addNode(NodeBase node) {
        if (this.child == null) {
            this.child = new LinkedList<NodeBase>();
        }
        this.child.add(node);
        node.setParent(this);
        node.ai = this.ai;
    }
}


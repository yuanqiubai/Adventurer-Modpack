/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai;

import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.BranchNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.RecursiveNode;
import com.adventurer_engine.common.entity.ai.task.TaskBase;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AIObject {
    private RecursiveNode root;
    private EntityRCsNPCBase host;
    private final List<TaskBase> currentTask = new LinkedList<TaskBase>();
    private final List<TaskBase> bufferTask = new LinkedList<TaskBase>();
    private boolean init = false;

    public AIObject(EntityRCsNPCBase host) {
        this.root = new RecursiveNode(this);
        this.root.ai = this;
        this.host = host;
    }

    public void addNode(NodeBase node) {
        this.root.addNode(node);
    }

    public void onTick() {
        if (!this.init) {
            this.root.setParent(null);
            this.root.actionTick(NodeStatus.INIT);
            this.init = true;
        }
        Iterator<TaskBase> iter = this.currentTask.iterator();
        while (iter.hasNext()) {
            TaskBase task = iter.next();
            NodeStatus status = task.actionTick(null);
            if (status == NodeStatus.RUNNING) continue;
            iter.remove();
            BranchNode parent = task.getParent();
            parent.actionTick(status);
        }
        if (!this.bufferTask.isEmpty()) {
            this.currentTask.addAll(this.bufferTask);
            for (TaskBase taskBase : this.bufferTask) {
                taskBase.init();
            }
            this.bufferTask.clear();
        }
    }

    public EntityRCsNPCBase getHost() {
        return this.host;
    }

    public void addTask(TaskBase task) {
        this.bufferTask.add(task);
    }

    public AIObject copy(EntityRCsNPCBase npc) {
        AIObject ai = new AIObject(npc);
        ai.root = (RecursiveNode)this.root.copy(ai);
        return ai;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.entity.ai;

import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.FallbackNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.ParallelNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.RecursiveNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.SequenceNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.ForceFailureNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.ForceRepeatNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.ForceSuccessNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.RepeatNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.RetryNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.decorator.ReverseNode;
import com.adventurer_engine.util.DebugGadgets;
import java.util.HashMap;
import java.util.Map;

public class NodeFactory {
    private static Map<String, Class<? extends NodeBase>> map = new HashMap<String, Class<? extends NodeBase>>();

    public static NodeBase createNode(String internalName, by nbt) {
        try {
            NodeBase node = map.get(internalName).newInstance();
            node.readNBT(nbt);
            return node;
        }
        catch (Exception e) {
            DebugGadgets.debugInfo(e.getMessage());
            return null;
        }
    }

    public static NodeBase createNode(by nbt) {
        try {
            String nodeName = nbt.i("type");
            NodeBase node = map.get(nodeName).newInstance();
            node.readNBT(nbt);
            return node;
        }
        catch (Exception e) {
            DebugGadgets.debugInfo(e.getMessage());
            return null;
        }
    }

    static {
        map.put("fallback", FallbackNode.class);
        map.put("parallel", ParallelNode.class);
        map.put("recursive", RecursiveNode.class);
        map.put("sequence", SequenceNode.class);
        map.put("forceFailure", ForceFailureNode.class);
        map.put("forceRepeat", ForceRepeatNode.class);
        map.put("forceSuccess", ForceSuccessNode.class);
        map.put("repeat", RepeatNode.class);
        map.put("retry", RetryNode.class);
        map.put("reverse", ReverseNode.class);
    }
}


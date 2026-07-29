/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.rcsjython.functions;

import com.adventurer_engine.util.rcsjython.PythonFunction;
import java.util.HashMap;

public class FunctionPrint
implements PythonFunction {
    @Override
    public Object execute(HashMap<String, Object> pars) {
        String toOutput = pars.containsKey("toOutput") ? (String)pars.get("toOutput") : "";
        return null;
    }

    public void internal_execute(String toOutput) {
        System.out.println(toOutput);
    }
}


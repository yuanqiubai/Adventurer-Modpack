/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.rcsjython;

import com.adventurer_engine.util.rcsjython.PythonFunction;
import com.adventurer_engine.util.rcsjython.datatype.DataType;
import com.adventurer_engine.util.rcsjython.functions.FunctionPrint;
import java.util.HashMap;

public class SimplePythonInterpreter {
    private static HashMap<String, PythonFunction> functions;
    private final HashMap<String, ? extends DataType> vars = new HashMap();

    public static void main(String[] args) {
        SimplePythonInterpreter test = new SimplePythonInterpreter();
        Class<SimplePythonInterpreter> c = SimplePythonInterpreter.class;
    }

    public void init() {
        functions = new HashMap();
        functions.put("print", new FunctionPrint());
    }

    public void executeFunction(String funName, HashMap<String, Object> vars, String ret) {
        if (functions.containsKey(funName)) {
            Object fret = functions.get(funName).execute(vars);
            if (fret == null) {
                return;
            }
            vars.put(ret, fret);
        }
    }
}


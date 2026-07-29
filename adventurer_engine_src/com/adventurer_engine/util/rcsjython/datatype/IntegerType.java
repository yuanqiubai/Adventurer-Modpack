/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.rcsjython.datatype;

import com.adventurer_engine.util.rcsjython.datatype.DataType;
import java.math.BigInteger;

public class IntegerType
implements DataType {
    private BigInteger value;

    public IntegerType(BigInteger value) {
        this.value = value;
    }

    public IntegerType(long value) {
        this.value = new BigInteger(String.valueOf(value));
    }

    public IntegerType(String value) {
        this.value = new BigInteger(value);
    }

    public BigInteger getValue() {
        return this.value;
    }
}


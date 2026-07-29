/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.rcsjython.datatype;

import com.adventurer_engine.util.rcsjython.datatype.DataType;
import java.math.BigDecimal;

public class FloatType
implements DataType {
    private BigDecimal value;

    public FloatType(BigDecimal value) {
        this.value = value;
    }

    public FloatType(long value) {
        this.value = new BigDecimal(String.valueOf(value));
    }

    public FloatType(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return this.value;
    }
}


package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{
    private final int value;

    public IntValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue castValue))
            return false;
        return this.value == castValue.value;
    }

}
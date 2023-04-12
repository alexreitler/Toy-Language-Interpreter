package model.expression;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.Value;

public class VarExp implements Exp{

    private final String key;

    public VarExp(String key) {
        this.key = key;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ADTException {
        return table.lookup(key);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(key);
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException, ADTException {
        return typeEnv.lookup(key);
    }
}

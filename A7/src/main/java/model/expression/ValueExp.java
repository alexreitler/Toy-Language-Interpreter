package model.expression;
import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.Value;

public class ValueExp implements Exp{

    private final Value value;

    public ValueExp(Value v) {
        this.value = v;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException {
        return this.value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(this.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException {
        return value.getType();
    }
}

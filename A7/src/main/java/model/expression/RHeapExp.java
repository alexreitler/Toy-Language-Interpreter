package model.expression;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.RefType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.RefValue;
import model.value.Value;

public class RHeapExp implements Exp{
    private final Exp expression;

    public RHeapExp(Exp expression) {
        this.expression = expression;
    }
    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException {
        Value value = expression.eval(table, heap);
        if (!(value instanceof RefValue refValue))
            throw new ExpEvalException(String.format("%s not of RefType", value));
        return heap.getValue(refValue.getAddress());
    }

    @Override
    public Exp deepCopy() {
        return new RHeapExp(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException, ADTException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new ExpEvalException("The rH argument is not a RefType.");
    }

}

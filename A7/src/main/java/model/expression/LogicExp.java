package model.expression;


import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.BoolType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.BoolValue;
import model.value.Value;

import java.util.Objects;

public class LogicExp implements Exp{

    private Exp expression1;

    private Exp expression2;

    private String operation;

    public LogicExp(Exp expression1, Exp expression2, String operation){
        this.expression1 = expression1;
        this.expression1 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException {
        Value value1, value2;
        value1 = this.expression1.eval(table, heap);
        if (!value1.getType().equals(new BoolType())) {throw new ExpEvalException("ERROR: First operand is not a boolean.");}
        else {
            value2 = this.expression2.eval(table, heap);
            if (!value2.getType().equals(new BoolType())) {throw new ExpEvalException("ERROR: Second operand is not a boolean.");}
            else {
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean b1, b2;
                b1 = bool1.getValue();
                b2 = bool2.getValue();
                if (Objects.equals(this.operation, "and")) {
                    return new BoolValue(b1 && b2);
                } else if (Objects.equals(this.operation, "or")) {
                    return new BoolValue(b1 || b2);
                }
            }

        }

        return null;
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(expression1.deepCopy(), expression2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException, ADTException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpEvalException("Second operand is not a boolean.");
        } else
            throw new ExpEvalException("First operand is not a boolean.");

    }

}

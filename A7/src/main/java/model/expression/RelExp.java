package model.expression;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

import java.util.Objects;

public class RelExp implements Exp{
    Exp expression1;
    Exp expression2;
    String operator;

    public RelExp(String operator, Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException {
        Value value1, value2;
        value1 = this.expression1.eval(table, heap);
        if (!value1.getType().equals(new IntType())) {throw new ExpEvalException("First operand in not an integer.");}
        else{
            value2 = this.expression2.eval(table, heap);
            if (!value2.getType().equals(new IntType())) {throw new ExpEvalException("Second operand is not an integer.");}
            else {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getValue();
                v2 = val2.getValue();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            }
        }
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new RelExp(operator, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return this.expression1.toString() + " " + this.operator + " " + this.expression2.toString();
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException, ADTException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new ExpEvalException("Second operand is not an integer.");
        } else
            throw new ExpEvalException("First operand is not an integer.");

    }

}

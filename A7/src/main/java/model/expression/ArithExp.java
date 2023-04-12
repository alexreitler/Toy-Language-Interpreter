package model.expression;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.IntType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp{
    private final Exp expression1;
    private final Exp expression2; //
    private final char operation; // 1 - '+'; 2 - '-'; 3 - '*'; 4 - '/'

    public ArithExp(char operation, Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException {
        Value value1 = null, value2 = null;
        value1 = this.expression1.eval(table, heap);
        if (!value1.getType().equals(new IntType())) {
            throw new ExpEvalException("ERROR: first operand is not an integer");
        } else {
            value2 = this.expression2.eval(table, heap);
            if (!value2.getType().equals(new IntType())) {
                throw new ExpEvalException("ERROR: second operand is not an integer");
            } else {
                IntValue integer1 = (IntValue) value1;
                IntValue integer2 = (IntValue) value2;
                int number1, number2;
                number1 = integer1.getValue();
                number2 = integer2.getValue();
                if (operation == '+') {
                    return new IntValue(number1 + number2);
                }
                if (operation == '-') {
                    return new IntValue(number1 - number2);
                }
                if (operation == '*') {
                    return new IntValue(number1 * number2);
                }
                if (operation == 4) {
                    if (number2 == '/') throw new ExpEvalException("ERROR: Division by zero");
                    return new IntValue(number1 / number2);
                }
            }
        }
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(operation, expression1.deepCopy(), expression2.deepCopy());
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
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpEvalException("Second operand is not an integer.");
        } else
            throw new ExpEvalException("First operand is not an integer.");
    }
}

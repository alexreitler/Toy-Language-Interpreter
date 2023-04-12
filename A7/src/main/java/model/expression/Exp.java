package model.expression;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.Value;

public interface Exp {

    Type typeCheck(IDictionary<String, Type> typeEnv) throws ExpEvalException, ADTException;

    Value eval(IDictionary<String, Value> table, IHeap heap) throws ExpEvalException, ADTException;

    Exp deepCopy();
}


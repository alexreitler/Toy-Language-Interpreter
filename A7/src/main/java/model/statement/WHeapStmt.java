package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.RefType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.value.RefValue;
import model.value.Value;

public class WHeapStmt implements IStmt{
    private final String varName;
    private final Exp expression;

    public WHeapStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        if (!symTable.is_defined(varName))
            throw new StmtExecException(String.format("Variable %s was not declared", varName));
        Value value = symTable.lookup(varName);
        if (!(value instanceof RefValue refValue))
            throw new StmtExecException(String.format("%s not of RefType", value));
        Value evaluated = expression.eval(symTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new StmtExecException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WHeapStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        if (typeEnv.lookup(varName).equals(new RefType(expression.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new StmtExecException("WriteHeap: right hand side and left hand side have different types.");
    }

}

package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "NopStatement";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        return typeEnv;
    }

}

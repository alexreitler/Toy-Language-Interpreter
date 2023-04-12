package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;

public interface IStmt {
    PrgState execute(PrgState state)  throws StmtExecException, ExpEvalException, ADTException;
    String toString();
    IStmt deepCopy();

    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException;
}
package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IStack;

public class CompStmt implements IStmt{
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second){
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state){
        IStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", first.toString(), second.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

}

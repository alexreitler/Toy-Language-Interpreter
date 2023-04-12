package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.MyDictionary;
import model.ADTs.IDictionary;
import model.ADTs.IStack;
import model.ADTs.MyStack;
import model.value.Value;

import java.util.Map;

public class ForkStmt implements IStmt{
    private final IStmt statement;
    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        IDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry: state.getSymTable().get_content().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().deepCopy());
        }

        return new PrgState(newStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
    }


    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

}

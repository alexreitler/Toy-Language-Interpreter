package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IList;
import model.value.Value;

public class PrintStmt implements IStmt{

    private final Exp expression;

    public PrintStmt(Exp expression){
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpEvalException, ADTException {
        IList<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        state.setOut(out);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

}
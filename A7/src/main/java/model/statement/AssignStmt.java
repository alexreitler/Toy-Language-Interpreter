package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;
import model.value.Value;

public class AssignStmt implements IStmt{

    private final String id;

    private final Exp expression;

    public AssignStmt(String id, Exp expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        if(!symTable.is_defined(id)){throw new StmtExecException("Variable was not declared");}
        else {
            Value val = expression.eval(symTable, state.getHeap());
            Type typId = (symTable.lookup(id)).getType();
            if (!val.getType().equals(typId)) {
                throw new StmtExecException("Type of variable and type of assignment are different");
            } else {
                symTable.update(id, val);
            }
        }
        state.setSymTable(symTable);

        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("%s = %s", id, expression.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new StmtExecException("Assignment: right hand side and left hand side have different types.");
    }

}

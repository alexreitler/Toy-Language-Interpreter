package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.BoolType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.ADTs.IStack;
import model.value.BoolValue;
import model.value.Value;

public class WhileStmt implements IStmt{
    private final Exp expression;
    private final IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        IStack<IStmt> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new StmtExecException(String.format("%s is not of BoolType", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new StmtExecException("The condition of WHILE does not have the type Bool.");
    }

}

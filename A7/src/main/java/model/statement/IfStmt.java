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

public class IfStmt implements IStmt{

    private final Exp expression;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(Exp exp, IStmt thenStmt, IStmt elseStmt){
        this.expression = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeap());
        if(!(result instanceof BoolValue boolResult)){throw new StmtExecException("Not a boolean expression");}
        else {
            IStmt statement;
            if(boolResult.getValue()){
                statement = thenStmt;
            } else {
                statement = elseStmt;
            }

            IStack<IStmt> stack = state.getExeStack();
            stack.push(statement);
            state.setExeStack(stack);
            return null;
        }
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}", expression.toString(), thenStmt.toString(), elseStmt.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            thenStmt.typeCheck(typeEnv.deepCopy());
            elseStmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new StmtExecException("The condition of IF does not have the type Bool.");
    }

}


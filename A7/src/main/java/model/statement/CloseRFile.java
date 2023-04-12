package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.StringType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    private final Exp expression;

    public CloseRFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new StmtExecException(String.format("%s is not of type StringType", expression));
        StringValue fileName = (StringValue) value;
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.is_defined(fileName.getValue()))
            throw new StmtExecException(String.format("%s is not present in the FileTable", value));
        BufferedReader br = fileTable.lookup(fileName.getValue());
        try {
            br.close();
        } catch (IOException e) {
            throw new StmtExecException(String.format("Error in closing %s", value));
        }
        fileTable.remove(fileName.getValue());
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StmtExecException("CloseReadFile requires a string expression.");
    }

}

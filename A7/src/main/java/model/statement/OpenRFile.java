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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private final Exp expression;

    public OpenRFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new StmtExecException(String.format("%s is not of StringType", expression.toString()));
        } else {
            StringValue fileName = (StringValue) value;
            IDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.is_defined(fileName.getValue())) {
                throw new StmtExecException(String.format("%s is already opened", fileName.getValue()));
            } else {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new StmtExecException(String.format("Couldn't open %s", fileName.getValue()));
                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StmtExecException("OpenReadFile requires a string expression.");
    }

}
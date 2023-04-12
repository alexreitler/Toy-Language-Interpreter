package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.expression.Exp;
import model.programstate.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.ADTs.IDictionary;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    private final Exp expression;
    private final String varName;

    public ReadFile(Exp expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtExecException, ExpEvalException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (!symTable.is_defined(varName)) {
            throw new StmtExecException(String.format("%s is not present in the symTable.", varName));
        } else {
            Value value = symTable.lookup(varName);
            if (!value.getType().equals(new IntType())) {
                throw new StmtExecException(String.format("%s is not of type IntType", value));
            } else {
                value = expression.eval(symTable, state.getHeap());
                if (!value.getType().equals(new StringType())) {
                    throw new StmtExecException(String.format("%s is not of type StringType", value));
                } else {
                    StringValue castValue = (StringValue) value;
                    if (!fileTable.is_defined(castValue.getValue())) {
                        throw new StmtExecException(String.format("The file table does not contain %s", castValue));
                    } else {
                        BufferedReader br = fileTable.lookup(castValue.getValue());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(varName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new StmtExecException(String.format("Can not read from file %s", castValue));
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(expression.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expression.toString(), varName);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            if (typeEnv.lookup(varName).equals(new IntType()))
                return typeEnv;
            else
                throw new StmtExecException("ReadFile requires an int as its variable parameter.");
        else
            throw new StmtExecException("ReadFile requires a string as es expression parameter.");
    }

}

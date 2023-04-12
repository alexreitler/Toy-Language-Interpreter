package model.statement;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.InterpreterException;
import exceptions.StmtExecException;
import model.programstate.PrgState;
import model.type.Type;
import model.ADTs.IDictionary;
import model.value.Value;

public class VarDeclStmt implements IStmt{
    private final String name;
    private final Type typ;

    public VarDeclStmt(String name, Type type){
        this.name = name;
        this.typ = type;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtExecException {
        IDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.is_defined(name)) {
            throw new StmtExecException("Variable " + name + " already exists in the symbol table.");
        }
        symTable.put(name, typ.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ);
    }

    @Override
    public String toString() {
        return String.format("%s %s", typ.toString(), name);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws StmtExecException, ExpEvalException, ADTException {
        typeEnv.put(name, typ);
        return typeEnv;
    }

}
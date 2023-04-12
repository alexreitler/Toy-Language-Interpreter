package model.programstate;

import exceptions.ADTException;
import exceptions.ExpEvalException;
import exceptions.StmtExecException;
import model.statement.IStmt;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.ADTs.IList;
import model.ADTs.IStack;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap heap;

    private final int id;

    private static int lastId = 0;

    public PrgState(IStack<IStmt> stack, IDictionary<String,Value> symTable, IList<Value> out, IDictionary<String, BufferedReader> fileTable, IHeap heap, IStmt program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        IStmt originalProgram = program.deepCopy();
        this.exeStack.push(originalProgram);
        this.heap = heap;
        this.id = setId();
    }

    public PrgState(IStack<IStmt> stack, IDictionary<String,Value> symTable, IList<Value> out, IDictionary<String, BufferedReader> fileTable, IHeap heap) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getId() {
        return this.id;
    }

    public void setExeStack(IStack<IStmt> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(IDictionary<String, Value> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(IList<Value> newOut) {
        this.out = newOut;
    }

    public void setFileTable(IDictionary<String, BufferedReader> newFileTable) {
        this.fileTable = newFileTable;
    }

    public void setHeap(IHeap newHeap) {
        this.heap = newHeap;
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public IList<Value> getOut() {
        return out;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public boolean isNotCompleted() {
        return exeStack.isEmpty();
    }

    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack = exeStack.getReversed();
        for (IStmt statement: stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key: symTable.get_key_set()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookup(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        for (Value elem: out.getList()) {
            outStringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.get_key_set()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.getValue(key)));
        }
        return heapStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nExecution stack: \n" + exeStack.getReversed() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\nFile table:\n" + fileTable.toString() + "\nHeap:\n" + heap.toString() + "\n";
    }

    public String programStateToString() throws ADTException {
        return "Id: " + id + "\nExecution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString() + "File table:\n" + fileTableToString() + "\nHeap:\n" + heapToString();
    }

    public PrgState oneStep() throws StmtExecException, ADTException, ExpEvalException {
        if (exeStack.isEmpty())
            throw new StmtExecException("Program state stack is empty!");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }
}

package repository;

import exceptions.ADTException;
import exceptions.InterpreterException;
import model.programstate.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> programStates;
    private final String logFilePath;

    public Repository(PrgState programState, String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
        this.emptyLogFile();
    }

    @Override
    public List<PrgState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramStates(List<PrgState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void addProgram(PrgState program) {
        this.programStates.add(program);
    }

    @Override
    public void logPrgStateExec(PrgState programState) throws IOException, ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.programStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}

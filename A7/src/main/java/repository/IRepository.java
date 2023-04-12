package repository;

import exceptions.ADTException;
import exceptions.InterpreterException;
import model.programstate.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getProgramList();
    void setProgramStates(List<PrgState> programStates);
    void addProgram(PrgState program);
    void logPrgStateExec(PrgState programState) throws IOException, ADTException;
    void emptyLogFile() throws IOException;
}

package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.List;

public interface IList<T> {
    void add(T elem);
    T pop() throws ADTException;
    List<T> getList();
    boolean isEmpty();
}

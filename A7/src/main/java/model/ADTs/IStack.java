package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.List;

public interface IStack<T> {
    T pop() throws ADTException;
    T peek();
    void push(T element);
    boolean isEmpty();
    List<T> getReversed();
}

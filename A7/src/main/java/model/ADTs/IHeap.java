package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;
import model.value.Value;

import java.util.HashMap;
import java.util.Set;

public interface IHeap {
    int getNextFree();
    void setContent(HashMap<Integer, Value> newMap);
    HashMap<Integer, Value> getContent();
    int addValue(Value value);
    Value getValue(Integer position) throws ADTException;
    void update(Integer position, Value value) throws ADTException;
    void remove(Integer key) throws ADTException;
    boolean containsKey(Integer position);
    Set<Integer> keySet();
}

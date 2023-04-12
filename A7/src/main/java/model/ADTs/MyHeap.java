package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;
import model.value.Value;

import java.util.HashMap;
import java.util.Set;

public class MyHeap implements IHeap{
    HashMap<Integer, Value> heap;
    Integer nextFreeLocation;

    public int newValue() {
        nextFreeLocation += 1;
        while (nextFreeLocation == 0 || heap.containsKey(nextFreeLocation))
            nextFreeLocation += 1;
        return nextFreeLocation;
    }

    public MyHeap() {
        this.heap = new HashMap<>();
        nextFreeLocation = 1;
    }

    @Override
    public int getNextFree() {
        return nextFreeLocation;
    }

    @Override
    public void setContent(HashMap<Integer, Value> newMap) {
        this.heap = newMap;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public int addValue(Value value) {
        heap.put(nextFreeLocation, value);
        Integer toReturn = nextFreeLocation;
        nextFreeLocation = newValue();
        return toReturn;
    }

    @Override
    public Value getValue(Integer position) throws ADTException {
        if (!heap.containsKey(position))
            throw new ADTException(String.format("%d cannot be found in the heap", position));
        return heap.get(position);
    }

    @Override
    public void remove(Integer key) throws ADTException {
        if (!containsKey(key))
            throw new ADTException(key + " is not defined.");
        nextFreeLocation = key;
        this.heap.remove(key);
    }

    @Override
    public void update(Integer position, Value value) throws ADTException {
        if (!heap.containsKey(position))
            throw new ADTException(String.format("%d cannot be found in the heap", position));
        heap.put(position, value);
    }

    @Override
    public boolean containsKey(Integer position) {
        return this.heap.containsKey(position);
    }

    @Override
    public Set<Integer> keySet() {
        return heap.keySet();
    }
    @Override
    public String toString() {
        return this.heap.toString();
    }
}

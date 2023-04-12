package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {

    List<T> list;

    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        this.list.add(value);
    }

    @Override
    public T pop() throws ADTException {
        if (list.isEmpty())
            throw new ADTException("The list is empty!");
        return this.list.remove(0);
    }

    @Override
    public List<T> getList() {
        return this.list;
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements IDictionary<T1, T2>{
    HashMap<T1, T2> dict;

    public MyDictionary()
    {
        this.dict = new HashMap<>();
    }

    @Override
    public void put(T1 k, T2 v) {
        this.dict.put(k,v);
    }

    @Override
    public boolean is_defined(T1 k) {
        return this.dict.containsKey(k);
    }

    @Override
    public Set<T1> get_key_set() {
        return dict.keySet();
    }

    @Override
    public void remove(T1 k) throws ADTException {
        if (!is_defined(k))
            throw new ADTException(k + " is not defined.");
        this.dict.remove(k);
    }

    @Override
    public Map<T1, T2> get_content() {
        return dict;
    }

    @Override
    public T2 lookup(T1 k) throws ADTException{
        if(!is_defined(k))
            throw new ADTException(k + "is not defined.");
        return this.dict.get(k);
    }

    @Override
    public void update(T1 k, T2 v) throws ADTException {
        if (!is_defined(k))
            throw new ADTException(k + " is not defined.");
        this.dict.put(k, v);
    }

    @Override
    public Collection<T2> values() {
        return this.dict.values();
    }

    @Override
    public String toString() {
        return this.dict.toString();
    }

    @Override
    public IDictionary<T1, T2> deepCopy() throws ADTException {
        IDictionary<T1, T2> toReturn = new MyDictionary<>();
        for (T1 key: dict.keySet())
            toReturn.put(key, lookup(key));
        return toReturn;
    }
}
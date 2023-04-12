package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<T1,T2> {
    void put(T1 key, T2 value);
    boolean is_defined(T1 key);
    Set<T1> get_key_set();
    void remove(T1 key) throws ADTException;
    Map<T1, T2> get_content();
    T2 lookup(T1 key) throws ADTException;
    void update(T1 key, T2 value) throws ADTException;
    Collection<T2> values();
    IDictionary<T1, T2> deepCopy() throws ADTException;
}

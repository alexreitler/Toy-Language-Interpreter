package model.ADTs;

import exceptions.ADTException;
import exceptions.InterpreterException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty())
            throw new ADTException("STACK ERROR: Stack is empty");
        return stack.pop();
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }
}


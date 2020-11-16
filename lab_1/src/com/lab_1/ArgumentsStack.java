package com.lab_1;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ArgumentsStack {
    LinkedList stack = new LinkedList();

    public void push(Object o) {
        stack.push(o);
    }

    public int size() {
       return stack.size();
    }

    public Object pop() {
        return stack.pop();
    }

    public void clear() {
        stack.clear();
    }
}

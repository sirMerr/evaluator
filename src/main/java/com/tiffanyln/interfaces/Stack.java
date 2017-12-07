package com.tiffanyln.interfaces;

public interface Stack<T> {
    /**
     * Adds member to the current end of the stack
     *
     * @param obj
     */
    void push(T obj);

    /**
     * Removes the last member added

     * @return T object
     */
    T pop();

    T peek();

    int size();
}

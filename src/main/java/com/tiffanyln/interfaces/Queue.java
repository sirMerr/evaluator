package com.tiffanyln.interfaces;

public interface Queue<T> {
    /**
     * Adds member to the end
     *
     * @param obj
     */
    void add(T obj);

    /**
     * Removes from front

     * @return T object
     */
    T remove();

    /**
     * Peek at front
     * @return
     */
    T element();

    int size();
}

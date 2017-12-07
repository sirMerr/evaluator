package com.tiffanyln.structures;

import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;

import java.util.ArrayList;

public class DynamicArray<T> implements Queue<T>, Stack<T> {

    // Dataset
    private ArrayList<T> list;
    // Index of first object in structure
    private int startPointer;
    // Index of last object in structure
    private int endPointer;

    /**
     * Initializes a DynamicArray with an initial capacity
     * of 5
     */
    public DynamicArray() {
        this(5);
    }

    /**
     * Initializes a DynamicArray with an initial capacity
     *
     * @param capacity
     *      Initial capacity
     */
    public DynamicArray(int capacity) {
        list = new ArrayList<>(capacity);
        startPointer = -1;
        endPointer = -1;
    }

    public DynamicArray(T[] array) {
        this(array.length);

    }

    /**
     * Adds member to the end
     *
     * @param obj
     */
    @Override
    public void add(T obj) {
    }

    /**
     * Removes from front
     *
     * @return T object
     */
    @Override
    public T remove() {
        return null;
    }

    /**
     * Peek at front
     *
     * @return
     */
    @Override
    public T element() {
        return null;
    }

    /**
     * Adds member to the current end of the stack
     *
     * @param obj
     */
    @Override
    public void push(T obj) {

    }

    /**
     * Removes the last member added
     *
     * @return T object
     */
    @Override
    public T pop() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}

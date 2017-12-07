package com.tiffanyln.structures;

import com.oracle.tools.packager.Log;
import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DynamicArray<T> implements Queue<T>, Stack<T> {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    // Dataset
    private ArrayList<T> list;
    // Index of first object in structure
    private int startPointer;
    // Index of last object in structure
    private int endPointer;

    private int pointer;

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
        // Set to -1 so that the pos will be 0 on the first push/add
        startPointer = -1;
        endPointer = -1;
    }

    public DynamicArray(T[] array) {
        this(array.length);

    }

    /**
     * Adds member to the end of stack
     *
     * @param obj
     */
    @Override
    public void push(T obj) {
        if (obj != null) {
            list.add(++startPointer, obj);
        }
    }

    /**
     * Removes from front of stack
     *
     * @return T object
     */
    @Override
    public T pop() {
        if (startPointer != -1) {
            T obj = list.get(startPointer);
            list.remove(startPointer--);
            return obj;
        }
        return null;
    }

    /**
     * Peek at front of stack
     *
     * @return
     */
    @Override
    public T peek() {
        if (startPointer != -1) {
            return list.get(startPointer);
        }

        return null;
    }

    /**
     * Adds member to the current end of the queue
     *
     * @param obj
     */
    @Override
    public void add(T obj) {
        if (obj != null) {
            list.add(++endPointer, obj);
        }

        log.debug("Push failed, obj is null");
    }

    /**
     * Removes the last member added of queue
     *
     * @return T object
     */
    @Override
    public T remove() {
        if (startPointer == endPointer) {
            return null;
        } else {
            // Get next item and remove it from list
            T obj = list.get(startPointer + 1);
            list.remove(startPointer);

            // Set pointers to accommodate new size
            startPointer--;
            endPointer--;
            return obj;
        }
    }

    /**
     * Returns the next value that will be popped
     *
     * @return next item that will be popped from the Queue or null
     *      if the Queue is empty
     */
    @Override
    public T element() {
        return startPointer==endPointer? null: list.get(startPointer + 1);
    }

    /**
     * Returns size of structure
     *
     * @return number of items in the structure
     */
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

package com.tiffanyln.structures;

import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class DynamicArray<T> implements Queue<T>, Stack<T> {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    // Dataset
    private Object[] list;
    // Index of first object in structure
    private int startPointer;
    // Index of last object in structure
    private int endPointer;
    private int capacity;
    private int size;

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
        list = new Object[capacity];
        // Set to -1 so that the pos will be 0 on the first push/add
        startPointer = -1;
        endPointer = -1;
        this.capacity = capacity;
    }

    public DynamicArray(T[] array) {
        this(array.length);

    }

    /**
     * Get the object at the position given
     * @param position
     * @return the object at the given position
     */
    public T get(int position) {
        return (T) list[position];
    }

    /**
     * Remove an object at the given positon and remove it
     *
     * @param position
     *      of the object to remove in the list
     * @return the element removed
     */
    public T remove(int position) {
        T obj = get(position);
        Object[] newList = new Object[capacity];
        int j = 0;

        // Make a copy of the array without the element at the
        // given position
        for (int i = 0; i < size(); i++) {
            // Only add if it's not the element we want to remove
            if (i != position) {
                newList[j++] = get(i);
            }
        }

        // Assign list to the updated copy
        list = Arrays.copyOf(newList, capacity);

        return obj;
    }

    /**
     * Adds member to the end of stack
     *
     * @param obj
     */
    @Override
    public void push(T obj) {
        if (obj != null) {
            if (startPointer < capacity) {
                list[++startPointer] = obj;
            }
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
            T obj = get(startPointer);
            remove(startPointer--);
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
            return get(startPointer);
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
            if (endPointer < capacity) {
                list[++endPointer] = obj;
            }
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
            T obj = get(startPointer + 1);
            remove(startPointer);

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
        return startPointer == endPointer? null: get(startPointer + 1);
    }

    /**
     * Returns size of structure
     *
     * @return number of items in the structure
     */
    @Override
    public int size() {
        return list.length;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

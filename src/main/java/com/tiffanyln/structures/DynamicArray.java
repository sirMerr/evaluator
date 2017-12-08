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
    private int size = 0;

    /**
     * Initializes a DynamicArray with an initial capacity
     * of 5
     */
    public DynamicArray() {
        this(0);
    }

    /**
     * Initializes a DynamicArray with an initial capacity
     *
     * @param capacity
     *      Initial capacity
     */
    public DynamicArray(int capacity) {
        log.debug("In DynamicArray constructor with capacity");
        list = new Object[capacity];
        // Set to -1 so that the pos will be 0 on the first push/add
        startPointer = -1;
        endPointer = -1;
        size = 0;

        this.capacity = capacity;
    }

    public DynamicArray(T[] arr) {
        this(arr.length);
        log.debug("In DynamicArray constructor with T[] arr");

        for (T obj : arr) {
            if (obj != null) {
                add(obj);
            }
        }
    }

    /**
     * Get the object at the position given
     * @param position
     * @return the object at the given position
     */
    private T get(int position) {
//        log.debug("In get() : positon ->" + position);
//        log.debug("Got: " + (T) list[position]);
        return (T) list[position];
    }

    /**
     * Remove an object at the given positon and remove it
     *
     * @param position
     *      of the object to remove in the list
     * @return the element removed
     */
    private T remove(int position) {
        log.debug("In remove(int positon): position ->" + position);
        T obj = get(position);
        Object[] newList = new Object[capacity];
        int j = 0;

        // Make a copy of the array without the element at the
        // given position
        for (int i = 0; i < size(); i++) {
            // Only add if it's not the element we want to remove
            if (i != position) {
                log.debug("index of item: " + i);
                newList[j++] = get(i);
            }
        }

        // Assign list to the updated copy
        list = Arrays.copyOf(newList, capacity);

        log.debug("Removed: " + obj);
        size--;
        return obj;
    }

    /**
     * Change the value at a specific position. The position must be less than the size
     *
     * @param position
     * @param obj
     */
    private boolean set(int position, T obj) {
        // Invalid
        if (obj == null || position < 0 || position > size) {
            return false;
        } else {
            list[position] = obj;
            return true;
        }
    }

    /**
     * Adds member to the end of stack
     *
     * @param obj
     */
    @Override
    public void push(T obj) {
        log.debug("In push(): obj -> " + obj);
        if (size >= capacity) {
            log.debug("Size is beyond capacity in push, incrementing capacity...");
            // Make a copy of the array without the element at the
            // given position
            list = Arrays.copyOf(list, ++capacity);
        }

        log.debug("In push()");
        if (obj != null) {
            if (startPointer < capacity) {
                list[++startPointer] = obj;
                size++;
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
        log.debug("In pop()");
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
        log.debug("In peek()");
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
        log.debug("In add(): obj -> " + obj);
        if (size >= capacity) {
            log.debug("Size is beyond capacity in add, incrementing capacity...");
            // Make a copy of the array without the element at the
            // given position
            list = Arrays.copyOf(list, ++capacity);
        }

        if (obj == null) {
            log.debug("Add failed, obj is null");
        } else {
            if (endPointer < capacity) {
                list[++endPointer] = obj;
                size++;
                log.debug("Add succeeded!");
            } else {
                log.debug("Add failed, endPointer: " + endPointer + " < " + "capacity: " + capacity);
            }
        }

    }

    /**
     * Removes the last member added of queue
     *
     * @return T object
     */
    @Override
    public T remove() {
        log.debug("In remove()");
        if (startPointer == endPointer) {
            return null;
        } else {
            // Get next item and remove it from list
            log.debug("Start pointer: " + startPointer);
            T obj = get(++startPointer);
            remove(startPointer);

            // Set pointers to accommodate new size
            startPointer--;
            endPointer--;
            log.debug("Removed(): " + obj);
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
        log.debug("In element()");
        return startPointer == endPointer? null: get(startPointer + 1);
    }

    /**
     * Returns size of structure
     *
     * @return number of items in the structure
     */
    @Override
    public int size() {
//        log.debug("In size(): size is " + size);
        return size;
    }

    @Override
    public String toString() {
        log.debug("toString()");
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < size(); i++ ) {
            T t = get(i);
            if (t != null) {
                stringBuilder.append(t);
            }
        }

        log.debug("List: " + stringBuilder.toString());
        return stringBuilder.toString();
    }
}

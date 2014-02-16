package com.github.barcon.esolang;

/**
 * @author Björn Hegstam
 * Date: 2013-05-22
 * Time: 19:48
 */
public class ArrayMemory {
    private int[] data;
    private int position;

    public ArrayMemory(int size) {
        data = new int[size];
        position = 0;
    }

    public void incrementPointer() {
        position++;
    }

    public void decrementPointer() {
        position--;
    }

    public int read() {
        return data[position];
    }

    public void write(int value) {
        data[position] = value;
    }

    public void increment() {
        data[position]++;
    }

    public void decrement() {
        data[position]--;
    }
}

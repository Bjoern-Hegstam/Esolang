package com.github.barcon.esolang;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Björn Hegstam
 * Date: 2013-05-22
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public class ArrayMemory {
    private int[] data;
    private int position;

    public ArrayMemory(int size) {
        data = new int[size];
        position = 0;
    }

    public void movePosition(int offset) {
        position += offset;
    }

    public int read() {
        return data[position];
    }

    public void write(int value) {
        data[position] = value;
    }
}

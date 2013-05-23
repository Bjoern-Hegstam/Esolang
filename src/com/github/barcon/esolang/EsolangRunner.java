package com.github.barcon.esolang;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Bear
 * Date: 2013-05-22
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class EsolangRunner {
    private final InputStream in;
    private final OutputStream out;

    public EsolangRunner(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public abstract void run(String line);
}

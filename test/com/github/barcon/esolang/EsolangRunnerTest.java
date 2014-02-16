package com.github.barcon.esolang;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class EsolangRunnerTest {
    private EsolangRunner runner;
    private ByteArrayOutputStream baos;

    @Before
    public void setUp() throws Exception {
        baos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(baos);


        runner = new EsolangRunner(new BrainfuckMapping(), null, ps);
    }

    @Test
    public void test_addition() throws Exception {
        // given
        String code = "+++.";

        // when
        runner.run(code);

        // then
        assertEquals(3, baos.toString().charAt(0));
    }
}

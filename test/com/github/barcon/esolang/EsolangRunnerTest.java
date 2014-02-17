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


        runner = new EsolangRunner(new TestLanguageMapping(), null, ps);
    }

    @Test
    public void addition() throws Exception {
        testCode("+++r", 3);
    }

    @Test
    public void simpleLoop() throws Exception {
        testCode("++[>++<-]>r", 4);
    }

    @Test
    public void nestedLoops() throws Exception {
        testCode("++[>++[>+++<-]<-]>>r", 12);
    }

    private void testCode(String code, int... expectedOutput) {
        runner.run(code);

        final String output = baos.toString();

        assertEquals(expectedOutput.length, output.length());
        for (int i = 0; i < output.length(); i++) {
            assertEquals(expectedOutput[i], output.charAt(i));
        }
    }
}

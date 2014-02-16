package com.github.barcon.esolang;

public class Main {
    public static void main(String[] args) {
        EsolangRunner runner = new EsolangRunner(new BrainfuckMapping(), System.in, System.out);
        runner.run("++++[>+++<-]>[<++++>-]<[>++<-]>+.");

        //runner.run(",>+++++[<.+>-]<.");
    }
}

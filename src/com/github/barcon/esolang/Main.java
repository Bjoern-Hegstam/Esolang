package com.github.barcon.esolang;

public class Main {

    public static void main(String[] args) {
        BrainfuckRunner runner = new BrainfuckRunner(System.in, System.out);
        runner.run("++++[>+++<-]>[<++++>-]<[>++<-]>+.");

        //runner.run(",>+++++[<.+>-]<.");
    }
}

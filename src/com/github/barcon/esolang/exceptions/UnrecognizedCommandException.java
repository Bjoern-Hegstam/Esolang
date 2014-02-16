package com.github.barcon.esolang.exceptions;

public class UnrecognizedCommandException extends RuntimeException {
    public UnrecognizedCommandException(char c, int code_idx) {
        super("Unrecognized command '" + c + "' at position " + code_idx);
    }
}

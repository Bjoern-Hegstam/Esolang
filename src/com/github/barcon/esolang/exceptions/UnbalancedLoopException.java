package com.github.barcon.esolang.exceptions;

public class UnbalancedLoopException extends RuntimeException {
    public UnbalancedLoopException(int code_idx) {
        super("Non-matching loop character at position " + code_idx);
    }
}

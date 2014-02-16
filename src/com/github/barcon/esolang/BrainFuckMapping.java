package com.github.barcon.esolang;

public class BrainFuckMapping extends LanguageMapping {
    @Override
    protected void build() {
        addToken(TokenType.LOOP_START, '[');
        addToken(TokenType.LOOP_END, ']');
        addToken(TokenType.INCREMENT, '+');
        addToken(TokenType.DECREMENT, '-');
        addToken(TokenType.INCREMENT_POINTER, '>');
        addToken(TokenType.DECREMENT_POINTER, '<');
        addToken(TokenType.READ, '.');
        addToken(TokenType.WRITE, ',');
    }
}

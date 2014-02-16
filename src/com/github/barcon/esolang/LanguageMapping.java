package com.github.barcon.esolang;

import java.util.HashMap;
import java.util.Map;

public abstract class LanguageMapping {
    private final Map<TokenType, Character> tokens = new HashMap<>();

    protected LanguageMapping() {
        build();
    }

    protected abstract void build();

    public char getToken(TokenType type) {
        return tokens.get(type);
    }

    public TokenType getType(char token) {
        return tokens.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(token))
                .findFirst()
                .get()
                .getKey();
    }

    protected void addToken(TokenType type, char token) {
        tokens.put(type, token);
    }
}

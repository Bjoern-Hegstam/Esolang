package com.github.barcon.esolang;

import java.util.HashMap;
import java.util.Map;

public abstract class LanguageMapping {
    private final Map<Character, TokenType> tokens = new HashMap<>();

    protected LanguageMapping() {
        build();
    }

    protected abstract void build();

    public TokenType getType(char token) {
        return tokens.get(token);
    }

    protected void addToken(TokenType type, char token) {
        tokens.put(token, type);
    }

    public boolean containsToken(char token) {
        return tokens.containsKey(token);
    }
}

package com.github.barcon.esolang;

import java.util.*;

public class Tokenizer {

    private final LanguageMapping mapping;

    public Tokenizer(LanguageMapping mapping) {
        this.mapping = mapping;
    }

    public List<Token> tokenize(String code) {
        final List<Token> tokens = new LinkedList<>();
        for (char c : code.toCharArray()) {
            if (mapping.containsToken(c)) {
                tokens.add(new Token(c, mapping.getType(c)));
            }
        }
        return tokens;
    }
}

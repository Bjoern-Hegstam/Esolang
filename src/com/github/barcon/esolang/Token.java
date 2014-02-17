package com.github.barcon.esolang;

final class Token {
    private final char character;
    private final TokenType type;

    Token(char character, TokenType type) {
        this.character = character;
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += 23 * character;
        hash += 23 * type.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof Token &&
                ((Token) obj).character == character &&
                ((Token) obj).type == type;
    }
}

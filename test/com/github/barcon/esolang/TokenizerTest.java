package com.github.barcon.esolang;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenizerTest {
    private Tokenizer tokenizer;
    private TestLanguageMapping mapping;

    @Before
    public void setUp() throws Exception {
        mapping = new TestLanguageMapping();
        tokenizer = new Tokenizer(mapping);
    }

    @Test
    public void tokenize_emptyString() throws Exception {
        // when
        final List<Token> tokens = tokenizer.tokenize("");

        // then
        assertTrue(tokens.isEmpty());
    }

    @Test
    public void tokenize_allTokenTypes() throws Exception {
        // given
        final String code = "+-<>[]rw";

        // when
        final List<Token> tokens = tokenizer.tokenize(code);

        // then
        final List<Token> expectedTokens = new LinkedList<>();
        for (char c : code.toCharArray()) {
            expectedTokens.add(new Token(c, mapping.getType(c)));
        }

        assertEquals(expectedTokens, tokens);
    }

    @Test
    public void tokenize_ignoreNonTokenCharacters() throws Exception {
        // given
        final String code = "+\n-test+";

        // when
        final List<Token> tokens = tokenizer.tokenize(code);

        // then
        final List<Token> expectedTokens = new LinkedList<>();
        expectedTokens.add(new Token('+', TokenType.INCREMENT));
        expectedTokens.add(new Token('-', TokenType.DECREMENT));
        expectedTokens.add(new Token('+', TokenType.INCREMENT));

        assertEquals(expectedTokens, tokens);
    }
}

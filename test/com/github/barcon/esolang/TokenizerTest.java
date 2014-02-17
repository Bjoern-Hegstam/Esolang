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
        final List<Token> tokenTypes = tokenizer.tokenize("");

        // then
        assertTrue(tokenTypes.isEmpty());
    }

    @Test
    public void tokenize_allTokenTypes() throws Exception {
        // given
        final String code = "+-<>[]rw";

        // when
        final List<Token> tokenTypes = tokenizer.tokenize(code);

        // then
        final List<Token> expectedTypes = new LinkedList<>();
        for (char c : code.toCharArray()) {
            expectedTypes.add(new Token(c, mapping.getType(c)));
        }

        assertEquals(expectedTypes, tokenTypes);
    }
}

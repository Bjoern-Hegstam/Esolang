package com.github.barcon.esolang;

import com.github.barcon.esolang.exceptions.UnbalancedLoopException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Björn Hegstam
 * Date: 2013-05-22
 * Time: 16:51
 */
public class EsolangRunner {
    public static final int DEFAULT_MEMORY_SIZE = 1000;
    private final LanguageMapping mapping;
    private final InputStream in;
    private final PrintStream out;
    private final ArrayMemory memory;
    private Map<TokenType, Command> commands;


    public EsolangRunner(LanguageMapping mapping, InputStream in, PrintStream out) {
        this.mapping = mapping;
        this.in = in;
        this.out = out;

        memory = new ArrayMemory(DEFAULT_MEMORY_SIZE);
        createCommands();
    }

    public InputStream getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }

    protected void createCommands() {
        commands = new HashMap<>();
        commands.put(TokenType.INCREMENT_POINTER, ArrayMemory::incrementPointer);
        commands.put(TokenType.DECREMENT_POINTER, ArrayMemory::decrementPointer);
        commands.put(TokenType.INCREMENT, ArrayMemory::increment);
        commands.put(TokenType.DECREMENT, ArrayMemory::decrement);

        commands.put(TokenType.WRITE, memory -> {
            try {
                memory.write(getIn().read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        commands.put(TokenType.READ, memory -> {
            getOut().write(memory.read());
            getOut().flush();
        });
    }

    public void run(String code) {
        final Tokenizer tokenizer = new Tokenizer(mapping);
        final List<Token> tokens = tokenizer.tokenize(code);

        Stack<Integer> loopStack = new Stack<>();

        int tokenIndex = 0;
        while (tokenIndex >= 0 && tokenIndex < tokens.size()) {
            final Token token = tokens.get(tokenIndex);
            if (commands.containsKey(token.getType())) {
                final Command command = commands.get(token.getType());
                command.execute(memory);
                tokenIndex++;
            } else if (token.getType() == TokenType.LOOP_START) {
                if (memory.read() != 0) {
                    loopStack.push(tokenIndex);
                    tokenIndex++;
                } else {
                    tokenIndex = findLoopEnd(tokenIndex, tokens);
                }
            } else if (token.getType() == TokenType.LOOP_END) {
                if (memory.read() != 0) {
                    if (loopStack.size() == 0) {
                        throw new UnbalancedLoopException(tokenIndex);
                    }

                    tokenIndex = loopStack.pop();
                } else {
                    loopStack.pop();
                    tokenIndex++;
                }
            } else {
                tokenIndex++;
            }
        }
    }

    private int findLoopEnd(int startIndex, List<Token> tokens) {
        Stack<Integer> loopStack = new Stack<>();
        loopStack.push(startIndex);
        int index = startIndex + 1;

        while (loopStack.size() > 0 && index < tokens.size()) {
            if (tokens.get(index).getType() == TokenType.LOOP_END) {
                loopStack.pop();
            } else if (tokens.get(index).getType() == TokenType.LOOP_START) {
                loopStack.push(index);
            }
            index++;
        }

        if (loopStack.size() > 0) {
            throw new UnbalancedLoopException(startIndex);
        }

        return index;
    }
}

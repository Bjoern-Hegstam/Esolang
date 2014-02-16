package com.github.barcon.esolang;

import com.github.barcon.esolang.exceptions.UnbalancedLoopException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Björn Hegstam
 * Date: 2013-05-22
 * Time: 16:51
 */
public class EsolangRunner {
    public static final char LOOP_START = '[';
    public static final char LOOP_END = ']';
    public static final int DEFAULT_MEMORY_SIZE = 1000;
    private final LanguageMapping mapping;
    private final InputStream in;
    private final PrintStream out;
    protected ArrayMemory memory;
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

        commands.put(TokenType.WRITE, data -> {
            try {
                data.write(getIn().read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        commands.put(TokenType.READ, data -> {
            getOut().write(data.read());
            getOut().flush();
        });
    }

    public void run(String code) {
        Stack<Integer> loops = new Stack<>();

        int code_idx = 0;
        while (code_idx >= 0 && code_idx < code.length()) {
            char command_char = code.charAt(code_idx);
            if (mapping.containsToken(command_char)) {
                final TokenType type = mapping.getType(command_char);
                commands.get(type).execute(memory);
                code_idx++;
            } else if (command_char == LOOP_START) {
                if (memory.read() != 0) {
                    loops.push(code_idx);
                    code_idx++;
                } else {
                    code_idx = findLoopEnd(code_idx, code);
                }
            } else if (command_char == LOOP_END) {
                if (memory.read() != 0) {
                    if (loops.size() == 0) {
                        throw new UnbalancedLoopException(code_idx);
                    }

                    code_idx = loops.pop();
                } else {
                    loops.pop();
                    code_idx++;
                }
            }
        }
    }

    private int findLoopEnd(int code_idx, String code) {
        Stack<Integer> loops = new Stack<>();
        loops.push(code_idx);

        int end_idx = code_idx + 1;
        do {
            if (code.charAt(end_idx) == LOOP_END) {
                loops.pop();
            } else {
                if (code.charAt(end_idx) == LOOP_START) {
                    loops.push(end_idx);
                }

                end_idx++;
            }
        } while (loops.size() > 0 && end_idx < code.length());

        if (loops.size() > 0) {
            throw new UnbalancedLoopException(code_idx);
        }

        return end_idx;
    }
}

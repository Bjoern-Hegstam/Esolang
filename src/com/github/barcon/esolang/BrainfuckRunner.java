package com.github.barcon.esolang;

import com.github.barcon.esolang.exceptions.UnbalancedLoopException;
import com.github.barcon.esolang.exceptions.UnrecognizedCommandException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author Björn Hegstam
 * Date: 2013-05-22
 * Time: 17:10
 */
public class BrainfuckRunner extends EsolangRunner {
    public static final char LOOP_START = '[';
    public static final char LOOP_END = ']';
    public static final int DEFAULT_MEMORY_SIZE = 1000;

    private Map<Character, Command> commands;
    private ArrayMemory memory;

    public BrainfuckRunner(InputStream in, OutputStream out) {
        super(in, out);

        memory = new ArrayMemory(DEFAULT_MEMORY_SIZE);

        createCommands();
    }

    private void createCommands() {
        commands = new HashMap<>();
        commands.put('>', ArrayMemory::incrementPointer);
        commands.put('<', ArrayMemory::decrementPointer);
        commands.put('+', ArrayMemory::increment);
        commands.put('-', ArrayMemory::decrement);

        commands.put(',', data -> {
            try {
                data.write(getIn().read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        commands.put('.', data -> {
            try {
                getOut().write(data.read());
                getOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run(String code) {
        Stack<Integer> loops = new Stack<>();

        int code_idx = 0;
        while (code_idx >= 0 && code_idx < code.length()) {
            char command_char = code.charAt(code_idx);
            if (commands.containsKey(command_char)) {
                commands.get(command_char).execute(memory);
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
            } else {
                throw new UnrecognizedCommandException(code.charAt(code_idx), code_idx);
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

package algoribrary.strings;

import java.util.Arrays;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class SuffixAutomaton {
    public final int[][] next;
    public final int[] link;
    public final int[] length;
    public final int offset;
    public final int alphabetSize;
    public int states;  // please, do not change it!

    public SuffixAutomaton(final int capacity, final int alphabetSize, final int offset) {
        this.next = new int[capacity][alphabetSize];
        this.link = new int[capacity];
        this.length = new int[capacity];
        this.alphabetSize = alphabetSize;
        this.offset = offset;
    }

    public SuffixAutomaton(final int capacity, final int alphabetSize) {
        this(capacity, alphabetSize, 0);
    }

    public SuffixAutomaton(final char[][] strings, final int alphabetSize, final int offset) {
        this.alphabetSize = alphabetSize;
        this.offset = offset;
        int capacity = 0;
        for (char[] string : strings) {
            capacity += string.length * 2;
        }
        next = new int[capacity][alphabetSize];
        link = new int[capacity];
        length = new int[capacity];
        for (int[] foo : next) {
            Arrays.fill(foo, -1);
        }
        Arrays.fill(link, -1);
        final int root = createState(0);
        for (char[] string : strings) {
            int current = root;
            for (char ch : string) {
                current = append(current, ch);
            }
        }
    }

    public SuffixAutomaton(final char[] string, final int alphabetSize, final int offset) {
        this(Math.max(2, string.length * 2 - 1), alphabetSize, offset);
        for (int[] foo : next) {
            Arrays.fill(foo, -1);
        }
        Arrays.fill(link, -1);
        int current = createState(0);
        for (char ch : string) {
            current = append(current, ch);
        }
    }

    public int append(int state, char label) {
        return append(state, label - offset);
    }

    public int append(int state, int label) {
        if (next[state][label] >= 0) {
            int current = next[state][label];
            if (length[current] != length[state] + 1) {
                int clone = cloneState(current, length[state] + 1);
                link[current] = clone;
                while (state >= 0 && next[state][label] == current) {
                    next[state][label] = clone;
                    state = link[state];
                }
                current = clone;
            }
            return current;
        }
        int current = createState(length[state] + 1);
        while (state >= 0 && next[state][label] < 0) {
            next[state][label] = current;
            state = link[state];
        }
        int suffix = 0;
        if (state >= 0) {
            suffix = next[state][label];
            if (length[suffix] != length[state] + 1) {
                int clone = cloneState(suffix, length[state] + 1);
                while (state >= 0 && next[state][label] == suffix) {
                    next[state][label] = clone;
                    state = link[state];
                }
                link[suffix] = clone;
                suffix = clone;
            }
        }
        link[current] = suffix;
        return current;
    }

    private int cloneState(int origin, int length) { // not sure, if length == length[link[origin]] + 1
        int clone = createState(length);
        System.arraycopy(next[origin], 0, next[clone], 0, alphabetSize);
        link[clone] = link[origin];
        return clone;
    }

    private int createState(int length) {
        this.length[states] = length;
        return states++;
    }
}

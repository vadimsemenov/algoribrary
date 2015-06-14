package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.SuffixAutomaton;

import java.io.PrintWriter;

public class Automaton {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] string = in.next().toCharArray();
        final int alphabetSize = 'z' - 'a' + 1;
        SuffixAutomaton suffixAutomaton = new SuffixAutomaton(string, alphabetSize, 'a');
        int vertices = suffixAutomaton.states;
        int edges = 0;
        for (int i = 0; i < vertices; ++i) {
            for (int next : suffixAutomaton.next[i]) {
                if (next != -1) {
                    edges++;
                }
            }
        }
        out.println(vertices + " " + edges);
        for (int i = 0; i < vertices; ++i) {
            for (int letter = 0; letter < alphabetSize; ++letter) {
                if (suffixAutomaton.next[i][letter] != -1) {
                    out.print(1 + i);
                    out.print(' ');
                    out.print(1 + suffixAutomaton.next[i][letter]);
                    out.print(' ');
                    out.println((char) ('a' + letter));
                }
            }
        }
        int terminal = -1;
        for (int i = 0; i < vertices; ++i) {
            if (suffixAutomaton.length[i] == string.length) {
                terminal = i;
                break;
            }
        }
        if (terminal == -1) {
            throw new AssertionError("No terminal vertex?oO");
        }
        int current = terminal;
        int terminalStates = 0;
        while (current != -1) {
            terminalStates++;
            current = suffixAutomaton.link[current];
        }
        out.println(terminalStates);
        current = terminal;
        while (current != -1) {
            out.print(current + 1);
            out.print(' ');
            current = suffixAutomaton.link[current];
        }
        out.println();
    }
}

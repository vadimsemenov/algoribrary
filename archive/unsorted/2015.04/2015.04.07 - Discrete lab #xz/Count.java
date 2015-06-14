package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.SuffixAutomaton;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Count {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] string = in.next().toCharArray();
        final int alphabet = 'z' - 'a' + 1;
        SuffixAutomaton automaton = new SuffixAutomaton(string, alphabet, 'a');
        List<Integer> order = new ArrayList<>();
        topsort(0, automaton, new boolean[automaton.states], order);
        long[] count = new long[automaton.states];
        for (int i : order) {
            for (int next : automaton.next[i]) {
                if (next != -1) {
                    count[i] += 1 + count[next];
                }
            }
        }
        out.println(count[0]);
    }

    private void topsort(int vertex, SuffixAutomaton automaton, boolean[] visited, List<Integer> order) {
        visited[vertex] = true;
        for (int next : automaton.next[vertex]) {
            if (next != -1 && !visited[next]) {
                topsort(next, automaton, visited, order);
            }
        }
        order.add(vertex);
    }
}

package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.SuffixAutomaton;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Common {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[][] strings = { in.next().toCharArray(), in.next().toCharArray() };
        SuffixAutomaton suffixAutomaton = new SuffixAutomaton(strings, 'z' - 'a' + 1, 'a');
        int root = 0;
        byte[] mask = new byte[suffixAutomaton.states];
        byte bit = 1;
        for (char[] string : strings) {
            int last = root;
            for (char ch : string) {
                last = suffixAutomaton.next[last][ch - 'a'];
            }
            while (last >= 0) {
                mask[last] |= bit;
                last = suffixAutomaton.link[last];
            }
            bit <<= 1;
        }
        List<Integer> order = new ArrayList<>();
        topsort(root, suffixAutomaton, new boolean[suffixAutomaton.states], order);
        for (int i : order) {
            for (int j : suffixAutomaton.next[i]) {
                if (j != -1) {
                    mask[i] |= mask[j];
                }
            }
        }
        byte both = mask[0];
        int maxLength = 0;
        for (int i = 0; i < suffixAutomaton.states; ++i) {
            if (mask[i] == both) {
                maxLength = Math.max(maxLength, suffixAutomaton.length[i]);
            }
        }
        int[] distance = new int[suffixAutomaton.states];
        Collections.reverse(order);
        for (int i : order) {
            if (mask[i] != both) continue;
            for (int j : suffixAutomaton.next[i]) {
                if (j != -1 && mask[j] == both) {
                    distance[j] = Math.max(distance[j], distance[i] + 1);
                }
            }
        }
        boolean[] belongsToLongest = new boolean[suffixAutomaton.states];
        for (int i = 0; i < suffixAutomaton.states; ++i) {
            if (mask[i] == both && suffixAutomaton.length[i] == maxLength) {
                belongsToLongest[i] = true;
            }
        }
        Collections.reverse(order);
        for (int i : order) {
            if (mask[i] != both) continue;
            for (int j : suffixAutomaton.next[i]) {
                if (j != -1 && distance[i] + 1 == distance[j]) {
                    belongsToLongest[i] |= belongsToLongest[j];
                }
            }
        }
        int current = root;
        for (int i = 0; i < maxLength; ++i) {
            for (char letter = 'a'; letter <= 'z'; ++letter) {
                int next = suffixAutomaton.next[current][letter - 'a'];
                if (next != -1 && belongsToLongest[next] && distance[current] + 1 == distance[next]) {
                    current = next;
                    out.print(letter);
                    break;
                }
            }
        }
    }

    private void topsort(int vertex, SuffixAutomaton automaton, boolean[] visited, List<Integer> order) {
        visited[vertex] = true;
        for (int next : automaton.next[vertex]) {
            if (next >= 0 && !visited[next]) {
                topsort(next, automaton, visited, order);
            }
        }
        order.add(vertex);
    }
}

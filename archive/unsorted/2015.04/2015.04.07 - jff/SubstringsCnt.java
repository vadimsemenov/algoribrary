package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.SuffixAutomaton;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SubstringsCnt {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int[] list = new int[10_000_000];
        for (int length = 2; length < 25; ++length) {
            int max = 0;
            int best = 0;
            int ptr = 0;
            for (int string = (1 << (length - 1)); string < (1 << length); ++string) {
                int cnt = countSubstrings(length, string);
                if (cnt > max) {
                    max = cnt;
                    best = string;
                    ptr = 0;
                }
                if (cnt == max) {
                    list[ptr++] = string;
                }
            }
            System.err.println(length + " " + (2 * ptr) + " " + max);
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < ptr; ++i) {
//                stringBuilder.append(Integer.toBinaryString(list[i]));
//                stringBuilder.append(' ');
//            }
            out.println(length + " " + (max + 1) + " " + (2 * ptr) + " " +
                    Integer.toBinaryString(best)/* + " " + stringBuilder*/);
        }
    }

    private int countSubstringsStupid(int length, int string) {
        boolean[][] has = new boolean[length + 1][(1 << length)];
        for (int from = 0; from < length; ++from) {
            for (int len = 0; from + len <= length; ++len) {
                int subString = (string >>> from) & ((1 << len) - 1);
                has[len][subString] = true;
            }
        }
        int cnt = 0;
        for (boolean[] ha : has) {
            for (boolean h : ha) {
                if (h) ++cnt;
            }
        }
        return cnt;
    }

    private int countSubstrings(int length, int string) {
        char[] str = new char[length];
        for (int i = 0; i < length; ++i) {
            str[i] = (char) ((string >> i) & 1);
        }
        SuffixAutomaton automaton = new SuffixAutomaton(str, 2, (char) 0);
        List<Integer> order = new ArrayList<>();
        topsort(0, automaton, new boolean[automaton.states], order);
        int[] count = new int[automaton.states];
        for (int i : order) {
            for (int next : automaton.next[i]) {
                if (next != -1) {
                    count[i] += 1 + count[next];
                }
            }
        }
        return count[0];
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

package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public class TaskJ {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        long answer = 0;
        int counter = in.nextInt();
        char[][] toponyms = new char[counter][];
        int length = 0;
        for (int i = 0; i < counter; ++i) {
            toponyms[i] = in.readLine().toCharArray();
            answer = Math.max(answer, toponyms[i].length);
            length += toponyms[i].length;
        }
        length += counter;
        int[] seq = new int[length];
        int ptr = 1;
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < toponyms[i].length; ++j) {
                seq[ptr++] = 1 + code(toponyms[i][j]);
            }
            ++ptr;
        }
        int[] next = new int[length];
        int prev = 0;
        for (int i = 1; i < length; ++i) {
            if (seq[i] == 0) {
                next[prev] = i;
                prev = i;
            }
        }
        next[prev] = length;
        int[] sa = StringUtils.buildSuffixArray(seq);
        int[] lcp = StringUtils.buildLCP(seq, sa);
        int[] stack = new int[counter + 1];
        ptr = 1;
        if (lcp[counter - 1] != 0) throw new AssertionError(lcp[counter - 1]);
        stack[0] = -1;
        for (int i = 0; i < counter; ++i) {
            lcp[i] = Math.min(lcp[i], Math.min(next[sa[i]] - sa[i], next[sa[i + 1]] - sa[i + 1]));
            --lcp[i];
            while (ptr > 1 && lcp[stack[ptr - 1]] >= lcp[i]) {
                --ptr;
                answer = Math.max(answer, lcp[stack[ptr]] * 1L * (i - stack[ptr - 1]));
            }
            stack[ptr++] = i;
        }
        out.println(answer);
    }

    private int code(char ch) {
        if ('a' <= ch && ch <= 'z') return ch - 'a';
        if ('A' <= ch && ch <= 'Z') return ch - 'A' + 26;
        return 26 + 26;
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] text = in.next().toCharArray();
        char[][] word = new char[2][];
        for (int it = 0; it < 2; it++) {
            word[it] = in.next().toCharArray();
        }
        int[] cnt = new int[256];
        int[][] _cnt = new int[2][256];
        for (char ch : text) ++cnt[ch];
        for (int it = 0; it < 2; ++it) {
            for (char ch : word[it]) ++_cnt[it][ch];
        }
        int fst = Integer.MAX_VALUE; int snd = Integer.MAX_VALUE;
        for (char ch : word[0]) fst = Math.min(fst, cnt[ch] / _cnt[0][ch]);
        for (char ch : word[1]) snd = Math.min(snd, cnt[ch] / _cnt[1][ch]);
        int best1 = -1;
        int best2 = -1;
        for (int first = 0; first <= fst; ++first) {
            int second = snd;
            for (char ch : word[0]) if (_cnt[1][ch] > 0) {
                second = Math.min(second, (cnt[ch] - first * _cnt[0][ch]) / _cnt[1][ch]);
            }
            if (first + second > best1 + best2) {
                best1 = first;
                best2 = second;
            }
        }
        for (int i = 0; i < best1; ++i) {
            out.print(String.valueOf(word[0]));
        }
        for (int i = 0; i < best2; ++i) {
            out.print(String.valueOf(word[1]));
        }
        for (int i = 0; i < 256; ++i) {
            for (int j = 0; j < cnt[i] - best1 * _cnt[0][i] - best2 * _cnt[1][i]; ++j) {
                out.print((char) i);
            }
        }
        out.println();
    }
}

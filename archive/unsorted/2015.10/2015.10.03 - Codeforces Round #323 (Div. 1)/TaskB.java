package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        final int max = 301;
        int n = in.nextInt();
        int t = in.nextInt();
        final int prefixLen = Math.min(n * n, n * t);
        final int suffixLen = Math.min(n * n, n * t - prefixLen);
        int[] a = in.nextIntArray(n);
        int[] cnt = new int[max];
        cnt[a[0]]++;
        for (int i = 1; i < a.length; ++i) {
            cnt[a[i]]++;
        }
        int[] fst = new int[max];
        for (int i = 0; i < prefixLen; ++i) {
            int foo = 0;
            for (int val = 0; val <= a[i % n]; ++val) {
                foo = Math.max(foo, fst[val]);
            }
            fst[a[i % n]] = foo + 1;
        }
        int[] snd = new int[max];
        for (int i = suffixLen - 1; i >= 0; --i) {
            int foo = 0;
            for (int val = a[i % n]; val < max; ++val) {
                foo = Math.max(foo, snd[val]);
            }
            snd[a[i % n]] = foo + 1;
        }
        int answer = 0;
        for (int i = 0; i < max; ++i) {
            for (int j = i; j < max; ++j) {
                for (int k = j; k < max; ++k) {
                    answer = Math.max(answer, fst[i] + (t - (prefixLen + suffixLen) / n) * cnt[j] + snd[k]);
                }
            }
        }
        out.println(answer);
    }
}

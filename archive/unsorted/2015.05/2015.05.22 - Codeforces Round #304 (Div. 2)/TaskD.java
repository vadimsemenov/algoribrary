
package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        final int maximal = 5_000_000;
        int[] lastDiv = new int[maximal + 1];
        for (int i = 0; i <= maximal; ++i) lastDiv[i] = i;
        for (int i = 2; i <= maximal; ++i) {
            if (lastDiv[i] == i) {
                for (int j = i + i; j <= maximal; j += i) {
                    lastDiv[j] = i;
                }
            }
        }
        long[] precalc = new long[maximal + 1];
        for (int i = 2; i <= maximal; ++i) {
            precalc[i] = 1 + precalc[i / lastDiv[i]];
        }
        for (int i = 1; i <= maximal; ++i) {
            precalc[i] += precalc[i - 1];
        }
        int tests = in.nextInt();
        for (int test = 0; test < tests; ++test) {
            int right = in.nextInt();
            int left = in.nextInt();
            out.println(precalc[right] - precalc[left]);
        }
    }
}

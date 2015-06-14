package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        long[] cnt = new long[10];
        List<Integer> output = new ArrayList<>();
        while (tests --> 0) {
            Arrays.fill(cnt, 0);
            output.clear();
            long from = in.nextLong();
            long to = in.nextLong();
            long max = -1;
            if (to - from > 99) {
                long _to = to / 9 * 9 - 9;
                long _from = (from + 8) / 9 * 9;
                for (long x = from; x < _from; ++x) {
                    cnt[(int) (x % 9)]++;
                }
                for (long x = _to; x <= to; ++x) {
                    cnt[(int) (x % 9)]++;
                }
            } else {
                for (long x = from; x <= to; ++x) {
                    cnt[(int) (x % 9)]++;
                }
            }
            for (long x : cnt) max = Math.max(max, x);
            cnt[9] = cnt[0];
            for (int root = 1; root <= 9; ++root) {
                if (cnt[root] == max) {
                    output.add(root);
                }
            }
            out.print(output.size());
            for (int i : output) {
                out.print(' ');
                out.print(i);
            }
            out.println();
        }
    }
}

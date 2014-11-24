package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] cnt = new int[100_001];
        for (int i = 0; i < counter; ++i) cnt[in.nextInt()]++;
        long[] d = new long[100_001 + 2];
        for (int i = 100_000; i >= 0; --i) {
            d[i] = Math.max(1L * i * cnt[i] + d[i + 2], d[i + 1]);
        }
        out.println(d[0]);
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int nodes = in.nextInt();
        int[] xs = new int[counter + 1];
        for (int i = 0; i < xs.length; ++i) {
            xs[i] = in.nextInt();
        }
        int step = xs[counter] / nodes;
        int[] dp = new int[counter + 1];
        Arrays.fill(dp, 1);
        for (int i = 0; i < counter; ++i) {
            int next = xs[i] + step;
            int id = Arrays.binarySearch(xs, i, counter, next);
            if (id >= 0) {
                dp[id] = Math.max(dp[id], dp[i] + 1);
                if (dp[id] == nodes) {
                    out.println(1);
                    return;
                }
            }
        }
        out.println(0);
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Bond {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        double[][] probability = new double[counter][counter];
        for (int i = 0; i < counter; i++) for (int j = 0; j < counter; j++)
            probability[i][j] = in.nextInt() / 100.0;
        double[] dp = new double[1 << counter];
        dp[0] = 1;
        for (int mask = 0; mask < (1 << counter); mask++) {
            int i = Integer.bitCount(mask);
            for (int task = 0; task < counter; task++) if ((1 & (mask >> task)) == 0) {
                dp[mask ^ (1 << task)] = Math.max(dp[mask ^ (1 << task)], dp[mask] * probability[i][task]);
big            }
        }
        out.println(100 * dp[(1 << counter) - 1]);
    }
}

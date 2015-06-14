package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        double[][] dp = new double[2][counter + 1];
        int[][][] length = new int[2][counter][counter];
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j <= i; ++j) {
                for (int k = 0; k < 2; ++k) {
                    length[k][i][j] = in.nextInt();
                }
            }
        }
        int cur = 0;
        for (int level = counter - 1; level >= 0; --level, cur ^= 1) {
            for (int i = 0; i <= level; ++i) {
                if (length[0][level][i] < length[1][level][i]) {
                    dp[cur ^ 1][i] = length[0][level][i] + dp[cur][i];
                } else if (length[0][level][i] > length[1][level][i]) {
                    dp[cur ^ 1][i] = length[1][level][i] + dp[cur][i + 1];
                } else {
                    dp[cur ^ 1][i] = length[0][level][i] + (dp[cur][i + 1] + dp[cur][i]) / 2;
                }
            }
        }
        out.println(dp[cur][0]);
    }
}

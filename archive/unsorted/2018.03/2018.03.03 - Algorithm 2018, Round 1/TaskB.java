package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        char[] s = in.next().toCharArray();
        for (int i = 0; i < s.length; ++i) s[i] -= '0';
        long[][] dp = new long[s.length + 1][1 << 10];
        for (int i = 0; i < s.length; ++i) {
            dp[i][0] = 1;
            int bit = 1 << s[i];
            for (int mask = 0; mask < (1 << 10); ++mask) {
                if ((mask & bit) == 0) {
                    int newMask = mask | bit;
                    for (int j = 0; j <= i; ++j) {
                        dp[i + 1][newMask] += dp[j][mask];
                    }
                }
            }
        }
//        long answer = 0;
//        for (int i = 0; i < dp.length; ++i) answer += dp[i][(1 << 10) - 1];
        out.println(dp[s.length][(1 << 10) - 1]);
//        out.println(answer);
    }
}

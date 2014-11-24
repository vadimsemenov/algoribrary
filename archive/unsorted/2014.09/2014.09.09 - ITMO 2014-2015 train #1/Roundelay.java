package tasks;



import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class Roundelay {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] height = new int[counter];
        for (int i = 0; i < counter; ++i) height[i] = in.nextInt();
        int[][] dp = new int[counter][counter];
        for (int i = 0; i < counter; ++i) {
            dp[i][i] = 1;
            if (i + 1 < counter) dp[i][i + 1] = height[i] == height[i + 1] ? 2 : 1;
        }
        for (int len = 3; len <= counter; ++len) {
            for (int i = 0; i + len <= counter; ++i) {
                int j = i + len - 1;
                if (height[i] == height[j]) dp[i][j] = Math.max(dp[i][j], 1 + dp[i + 1][j - 1]);
                if (height[i] == height[i + 1]) dp[i][j] = Math.max(dp[i][j], 1 + dp[i + 2][j]);
                if (height[j - 1] == height[j]) dp[i][j] = Math.max(dp[i][j], 1 + dp[i][j - 2]);
                dp[i][j] = Math.max(dp[i][j], Math.max(dp[i + 1][j], dp[i][j - 1]));
                for (int mid = i + 2; mid < j - 1; ++mid) {
                    if (height[i] == height[mid]) dp[i][j] = Math.max(dp[i][j], dp[i + 1][mid - 1] + dp[mid + 1][j]);
                    if (height[mid] == height[j]) dp[i][j] = Math.max(dp[i][j], dp[i][mid - 1] + dp[mid + 1][j - 1]);
                }
            }
        }
        out.println(dp[0][counter - 1]);
    }
}

// 19 12 0 19 17 3 15 4 11 15 4  0  3  5  16 16 6  16 10 14
// 0   1 2  3  4 5  6 7  8  9 10 11 12 13 14 15 16 17 18 19
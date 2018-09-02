package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskB {
    private static final long INF = Long.MAX_VALUE / 3;

    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        int slow = in.nextInt();
        int fast = in.nextInt();
        int tap = in.nextInt();
        boolean[][] contains = new boolean[qty][26];
        for (int i = 0; i < qty; ++i) {
            char[] keyboard = in.next().toCharArray();
            for (char ch : keyboard) contains[i][ch - 'a'] = true;
        }
        char[] text = in.next().toCharArray();
        long[][][] dp = new long[2][qty][2];
        int cur = 0;
        for (long[] t : dp[cur]) Arrays.fill(t, INF);
        for (long[] t : dp[cur ^ 1]) Arrays.fill(t, INF);
        dp[cur][0][0] = 0;
        if (qty > 1) {
            dp[cur][1][1] = slow;
            for (int i = 2; i < qty; ++i) dp[cur][i][1] = dp[cur][i - 1][1] + fast;
        }
        for (char ch : text) {
            int next = cur ^ 1;
            ch -= 'a';
            for (int i = 0; i < qty; ++i) {
                if (contains[i][ch]) {
                    dp[next][i][0] = Math.min(dp[next][i][0], Math.min(dp[cur][i][0], dp[cur][i][1]) + tap);
                }
                int j = (i + 1) % qty;
                dp[next][j][1] = Math.min(dp[next][j][1], Math.min(dp[next][i][0] + slow, dp[next][i][1] + fast));
            }
            for (int i = 0; i < qty; ++i) {
                int j = (i + 1) % qty;
                dp[next][j][1] = Math.min(dp[next][j][1], Math.min(dp[next][i][0] + slow, dp[next][i][1] + fast));
            }
            for (long[] t : dp[cur]) Arrays.fill(t, INF);
            cur = next;
        }
        long answer = INF;
        for (long[] t : dp[cur]) answer = Math.min(answer, Arrays.stream(t).min().orElseGet(() -> INF));
        out.println(answer == INF ? -1 : answer);
    }
}

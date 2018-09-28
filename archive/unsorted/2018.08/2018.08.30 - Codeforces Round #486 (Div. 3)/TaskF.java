package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskF {
    private static final int INF = 1000_000_000;

    public void solve(int __, InputReader in, PrintWriter out) {
        int distance = in.nextInt();
        int rainSegmentsQty = in.nextInt();
        int umbrellaQty = in.nextInt();
        boolean[] begins = new boolean[distance + 2];
        boolean[] ends = new boolean[distance + 2];
        for (int i = 0; i < rainSegmentsQty; ++i) {
            begins[in.nextInt() + 1] = true;
            ends[in.nextInt() + 1] = true;
        }
        int[] road = new int[distance + 2];
        for (int i = 0; i < umbrellaQty; ++i) {
            int x = in.nextInt() + 1;
            int weight = in.nextInt();
            if (road[x] > 0) {
                road[x] = Math.min(road[x], weight);
            } else {
                road[x] = weight;
            }
        }
        int[][] dp = new int[2][distance + 2];
        int prev = 0;
        Arrays.fill(dp[prev], INF);
        Arrays.fill(dp[prev ^ 1], INF);
        dp[prev][0] = 0;
        for (int i = 1; i < road.length; ++i) {
            int cur = prev ^ 1;
            int min = INF;
            for (int j = 0; j < i; ++j) {
                dp[cur][j] = dp[prev][j];
                if (road[j] != 0) dp[cur][j] += road[j];
                min = Math.min(min, dp[cur][j]);
            }
            if (road[i] > 0) {
                dp[cur][i] = min;
            }
            assert !(begins[i] && ends[i]);
            if (begins[i]) {
                dp[cur][0] = INF;
            }
            if (ends[i]) {
                dp[cur][0] = min;
            }
            prev = cur;
        }
        int min = INF;
        for (int res : dp[prev]) {
            min = Math.min(min, res);
        }
        out.println(min == INF ? -1 : min);
    }
}

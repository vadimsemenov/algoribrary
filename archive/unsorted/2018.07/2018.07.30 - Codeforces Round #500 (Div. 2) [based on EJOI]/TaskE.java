package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] height = in.nextIntArray(count);
        final int TAKE_NOTHING = 0;
        final int TAKE_PREVIOUS = 1;
        final int TAKE_CURRENT = 2;
        long[][][] dp = new long[2][(count + 1) / 2 + 1][3];
        int cur = 0;
        for (long[] d : dp[cur]) Arrays.fill(d, Long.MAX_VALUE / 3);
        dp[cur][0][TAKE_NOTHING] = 0;
        dp[cur][1][TAKE_CURRENT] = count > 1 ? Math.max(0, height[1] - height[0] + 1) : 0;
        for (int i = 0; i + 1 < count; ++i) {
            int next = cur ^ 1;
            for (long[] d : dp[next]) Arrays.fill(d, Long.MAX_VALUE / 3);
            for (int k = 0; k <= (i + 2) / 2; ++k) {
                dp[next][k][TAKE_NOTHING] = Math.min(dp[next][k][TAKE_NOTHING], dp[cur][k][TAKE_NOTHING]);
                dp[next][k][TAKE_NOTHING] = Math.min(dp[next][k][TAKE_NOTHING], dp[cur][k][TAKE_PREVIOUS]);
                dp[next][k][TAKE_PREVIOUS] = Math.min(dp[next][k][TAKE_PREVIOUS], dp[cur][k][TAKE_CURRENT]);
                if (k < (count + 1) / 2) {
                    int destroyNext = i + 2 < count ? Math.max(0, height[i + 2] - height[i + 1] + 1) : 0;
                    int destroyPrev = Math.max(0, height[i] - height[i + 1] + 1);
                    dp[next][k + 1][TAKE_CURRENT] = Math.min(dp[next][k + 1][TAKE_CURRENT], dp[cur][k][TAKE_NOTHING] + destroyPrev + destroyNext);
                    destroyPrev = i > 0 ? Math.max(0, Math.min(height[i], height[i - 1] - 1) - height[i + 1] + 1) : destroyPrev;
                    dp[next][k + 1][TAKE_CURRENT] = Math.min(dp[next][k + 1][TAKE_CURRENT], dp[cur][k][TAKE_PREVIOUS] + destroyPrev + destroyNext);
                }
            }
            cur = next;
        }
        for (int k = 1; k <= (count + 1) / 2; ++k) {
            if (k > 1) {
                out.print(' ');
            }
            long answer = Long.MAX_VALUE;
            for (int state = 0; state < 3; ++state) {
                answer = Math.min(answer, dp[cur][k][state]);
            }
            out.print(answer);
        }
    }
}

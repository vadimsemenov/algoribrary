package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    private static final int MODULO = 998244353;

    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int[][] distance = new int[4][4];
        for (int oldMask = 0; oldMask < 4; ++oldMask) {
            for (int newMask = 0; newMask < 4; ++newMask) {
                distance[oldMask][newMask] = distance(oldMask, newMask);
            }
        }
        int[][][] dp = new int[2][4][k + 1];
        boolean[][][] possible = new boolean[2][4][k + 1];
        int cur = 0;
        dp[cur][0][1] = dp[cur][3][1] = 1;
        possible[cur][0][1] = possible[cur][3][1] = true;
        if (2 <= k) {
            dp[cur][1][2] = dp[cur][2][2] = 1;
            possible[cur][1][2] = possible[cur][2][2] = true;
        }
        for (int i = 1; i < n; ++i) {
            int next = cur ^ 1;
            for (int[] d : dp[next]) Arrays.fill(d, 0);
            for (boolean[] d : possible[next]) Arrays.fill(d, false);
            for (int oldMask = 0; oldMask < 4; ++oldMask) {
                for (int count = 0; count <= k; ++count) {
                    if (possible[cur][oldMask][count]) {
                        for (int newMask = 0; newMask < 4; ++newMask) {
                            int ad = distance[oldMask][newMask];
                            if (count + ad > k) continue;
                            possible[next][newMask][count + ad] = true;
                            dp[next][newMask][count + ad] = (dp[next][newMask][count + ad] + dp[cur][oldMask][count]) % MODULO;
                        }
                    }
                }
            }
            cur = next;
        }
        long answer = 0;
        for (int mask = 0; mask < 4; ++mask) {
            if (possible[cur][mask][k]) {
                answer += dp[cur][mask][k];
            }
        }
        out.println(answer % MODULO);
    }

    private int distance(int oldMask, int newMask) {
        boolean oldSame = same(oldMask);
        boolean newSame = same(newMask);
        if (oldMask == newMask) {
            return 0;
        } else if (oldSame && !newSame) {
            return 1;
        } else if (!oldSame && newSame) {
            return 0;
        } else if (oldSame && newSame) {
            return 1;
        } else if (!oldSame && !newSame) {
            return 2;
        } else throw new IllegalStateException("POLUNDRA");
    }

    private boolean same(int oldMask) {
        return oldMask == 0 || oldMask == 3;
    }
}

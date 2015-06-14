package tasks;

import java.util.Arrays;

public class Mutalisk {
    private static final int INF = Integer.MAX_VALUE / 3;
    public int minimalAttacks(int[] hp) {
        int left = -1;
        int right = 1;
        for (int _hp : hp) right += (_hp + 8) / 9;
        while (right - left > 1) {
            int limit = (left + right) >>> 1;
            int[][][] dp = new int[2][limit + 1][limit + 1];
            int cur = 0;
            for (int[] _dp : dp[cur]) Arrays.fill(_dp, INF);
            dp[cur][0][0] = 0;
            for (int id = 0; id < hp.length; ++id, cur ^= 1) {
                for (int[] _dp : dp[cur ^ 1]) Arrays.fill(_dp, INF);
                for (int fst = 0; fst <= (hp[id] + 8) / 9; ++fst) {
                    for (int snd = 0; snd <= Math.max(0, hp[id] - fst * 9 + 2) / 3; ++snd) {
                        int thr = Math.max(0, hp[id] - fst * 9 - snd * 3);
                        if (fst + snd + thr > limit) continue;
                        for (int first = 0; first + fst <= limit; ++first) {
                            for (int second = 0; second + snd <= limit; ++second) {
                                if (dp[cur][first][second] + thr <= limit) {
                                    dp[cur ^ 1][first + fst][second + snd] = Math.min(
                                            dp[cur ^ 1][first + fst][second + snd],
                                            dp[cur][first][second] + thr);
                                }
                            }
                        }
                    }
                }
            }
            boolean ok = false;
            for (int i = 0; i <= limit; ++i) for (int j = 0; j <= limit; ++j) {
                ok |= dp[cur][i][j] <= limit;
            }
            if (ok) {
                right = limit;
            } else {
                left = limit;
            }
        }
        return right;
    }
}

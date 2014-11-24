package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TShirts {
    private static final int MODULO = 1_000_000_000;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] id = new int[counter];
        for (int i = 0; i < counter; ++i) id[i] = in.nextInt();
        if (counter == 1) {
            out.println(1);
            return;
        }
        int[][] dp = new int[counter][counter];
        Arrays.fill(dp[0], 1);
        Arrays.fill(dp[1], 2);
        for (int i = 0; i < counter - 1; ++i) {
            if (id[i] == id[i + 1]) {
                dp[1][i] = 3;
            }
        }
        for (int d = 2; d < counter; ++d) {
            for (int i = 0; i + d < counter; ++i) {
                int j = i + d;
                int res = 0;
                if (id[i] == id[j]) {
                    res += dp[d - 2][i + 1] + 1;
                    res += dp[d - 1][i];
                    if (res >= MODULO) res -= MODULO;
                    res += dp[d - 1][i + 1];
                    if (res >= MODULO) res -= MODULO;
                    res -= dp[d - 2][i + 1];
                    if (res < 0) res += MODULO;
                } else {
                    res += dp[d - 1][i];
                    res += dp[d - 1][i + 1];
                    if (res >= MODULO) res -= MODULO;
                    res -= dp[d - 2][i + 1];
                    if (res < 0) res += MODULO;
                }
                dp[d][i] = res;
            }
        }
        out.println(dp[counter - 1][0]);
    }
}

package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TaskF {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {

//        int tests = in.nextInt();


        final int max = in.nextInt();
        final int lower = 2;
        final int upper = 7;
        boolean[][] ch = new boolean[max + 1][upper + 1];
        for (int k = lower; k <= upper; k++) {
            int cnt = 0;
            for (int i = 1; i <= max; i++) {
                ch[i][k] = check(i, k);
                if (ch[i][k]) ++cnt;
            }
            out.println(cnt + "!!!");
        }
        for (int k = lower; k <= upper; k++) {
            boolean[] good = new boolean[max + 1];
            out.print("{");
            for (int a = 2; a <= max; a++) {
                good[a] = true;
                for (int i = 2; i < a; i++) {
                    if (a % i == 0 && good[i]) {
                        good[a] = false;
                        break;
                    }
                }
                if (!good[a]) {
                    continue;
                }
                for (int i = 1; i <= max; i++) {
                    if (i % a == 0 && !ch[i][k]) {
                        good[a] = false;
                        break;
                    }
                }
                if (good[a]) {
                    out.print(a + ", ");
                }
            }
            out.println("},");
        }

        BigInteger lcm = BigInteger.ONE;
        for (int i : new int[]{7, 8, 9, 10, 12, 15, 22, 33, 39, 52, 55, 68, 102, 114, 138}) {
            BigInteger cur = BigInteger.valueOf(i);
            lcm = lcm.multiply(cur).divide(lcm.gcd(cur));
        }
        out.println(lcm);

//        for (int k = lower; k <= upper; ++k) {
//            out.print(k + ": ");
//            for (int i = 1; i <= max; ++i) {
//                if (check(i, k)) {
//                    out.print(i + " ");
//                }
//            }
//            out.println();
//        }
    }

    private boolean check(int n, int k) {
        List<Integer> divs = new ArrayList<>();
        for (int d = 1; d * d <= n; ++d) {
            if (n % d == 0) {
                divs.add(d);
                if (n / d != d) {
                    divs.add(n / d);
                }
            }
        }
        boolean[][] dp = new boolean[k + 1][n + 1];
        dp[0][0] = true;
        for (int kk = 1; kk <= k; ++kk) {
            for (int w : divs) {
                for (int j = 0; j <= n - w; ++j) {
                    dp[kk][j + w] |= dp[kk - 1][j];
                }
            }
        }
        return dp[k][n];
    }
}

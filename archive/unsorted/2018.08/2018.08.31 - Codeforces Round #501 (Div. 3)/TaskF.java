package task;



import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public final class TaskF {
    private static final int MODULO = 1_000_000_000 + 7;

    private String subsequence;
    private int[][] nextPosition;
    private int[][][][] dp;

    public void solve(int __, InputReader in, PrintWriter out) {
        int maxBalance = in.nextInt();
        int length = 2 * maxBalance;
        subsequence = in.next();
        nextPosition = new int[subsequence.length() + 1][2];
        for (int i = 0; i <= subsequence.length(); ++i) {
            for (int type = 0; type < 2; ++type) {
                char parenthesis = "()".charAt(type);
                int k;
                link:
                for (k = Math.min(subsequence.length(), i + 1); k > 0; --k) {
                    if (subsequence.charAt(k - 1) != parenthesis) continue;
                    for (int j = k - 1; j --> 0; ) {
                        if (subsequence.charAt(j) != subsequence.charAt(i - k + 1 + j)) {
                            continue link;
                        }
                    }
                    break;
                }
                nextPosition[i][type] = k;
            }
        }
        dp = new int[length + 1][maxBalance + 1][subsequence.length() + 1][2];
        dp[0][0][0][0] = 1;
        for (int len = 0; len < length; ++len) {
            for (int balance = 0; balance <= Math.min(len, maxBalance); ++balance) {
                for (int pos = 0; pos <= Math.min(len, subsequence.length()); ++pos) {
                    for (int was = 0; was < 2; ++was) {
                        if (dp[len][balance][pos][was] == 0) continue;
                        if (balance < maxBalance) {
                            update(len, balance, pos, was, '(');
                        }
                        if (balance > 0) {
                            update(len, balance, pos, was, ')');
                        }
                    }
                }
            }
        }
        int sum = 0;
        for (int pos = 0; pos <= subsequence.length(); ++pos) {
            sum = sum(sum, dp[length][0][pos][1]);
        }
        out.println(sum);
    }

    private void update(int len, int balance, int pos, int was, char parenthesis) {
        int nextLen = len + 1;
        int nextBalance = balance + (parenthesis == '(' ? 1 : -1);
        int nextPos = nextPosition[pos]["()".indexOf(parenthesis)];
        int nextWas = was | (nextPos == subsequence.length() ? 1 : 0);
        dp[nextLen][nextBalance][nextPos][nextWas] =
                sum(dp[nextLen][nextBalance][nextPos][nextWas], dp[len][balance][pos][was]);
    }

    private int sum(int first, int second) {
        return (first + second) % MODULO;
    }
}

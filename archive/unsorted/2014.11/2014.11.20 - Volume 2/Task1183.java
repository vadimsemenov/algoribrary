package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Task1183 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] input = in.next().toCharArray();
        int[][] dp = new int[input.length][input.length];
        for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE / 10);
        for (int i = 0; i < input.length; ++i) dp[i][i] = 1;
        for (int len = 2; len <= input.length; ++len) {
            for (int i = 0; i + len - 1 < input.length; ++i) {
                int j = i + len - 1;
                dp[i][j] = Math.min(1 + dp[i][j - 1], 1 + dp[i + 1][j]);
                for (int k = i; k <= j; ++k) {
                    if ((input[i] == '[' && input[k] == ']') || (input[i] == '(' && input[k] == ')')) {
                        dp[i][j] = Math.min(dp[i][j], ((i + 1 <= k - 1) ? dp[i + 1][k - 1] : 0) + (k < j ? dp[k + 1][j] : 0));
                    }
                    if ((input[k] == '[' && input[j] == ']') || (input[k] == '(' && input[j] == ')')) {
                        dp[i][j] = Math.min(dp[i][j], ((k + 1 <= j - 1) ? dp[k + 1][j - 1] : 0) + (i < k ? dp[i][k - 1] : 0));
                    }
                }
            }
        }
        print(input, dp, 0, input.length - 1, out);
    }

    private void print(char[] input, int[][] dp, int i, int j, PrintWriter out) {
        if (i > j) {
        } else if (i == j) {
            if (input[i] == '(' || input[i] == ')') {
                out.print("()");
            } else if (input[i] == '[' || input[i] == ']') {
                out.print("[]");
            } else throw new AssertionError(input[i]);
        } else if ((input[i] == '(' && input[j] == ')') || (input[i] == '[' && input[j] == ']') &&
                    dp[i][j] == (i + 1 <= j - 1 ? dp[i + 1][j - 1] : 0)) {
            out.print(input[i]);
            print(input, dp, i + 1, j - 1, out);
            out.print(input[j]);
        } else {
            for (int k = i; k < j; ++k) {
                if (dp[i][j] == dp[i][k] + dp[k + 1][j]) {
                    print(input, dp, i, k, out);
                    print(input, dp, k + 1, j, out);
                    break;
                }
            }
        }
    }
}

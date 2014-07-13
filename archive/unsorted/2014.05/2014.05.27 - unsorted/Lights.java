package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Lights {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        char[] first = in.next().toCharArray();
        char[] second = in.next().toCharArray();
        int[][] dp = new int[4][counter];
        for (int i = 0; i < 4; i++) Arrays.fill(dp[i], 100_000);
        for (int i = 0; i < counter; i++) {
            if (first[i] == '0' && second[i] == '0') {
                if (i > 0) {
                    for (int k = 0; k < 4; k++) {
                        dp[0][i] = Math.min(dp[0][i], dp[k][i - 1]);
                    }
                    dp[3][i] = Math.min(dp[3][i], dp[3][i - 1] + 1);
                } else {
                    dp[0][i] = 0;
                }
            } else if (first[i] == '1' && second[i] == '0') {
                if (i > 0) {
                    dp[1][i] = Math.min(
                            dp[1][i - 1],
                            dp[3][i - 1]
                    );
                    for (int k = 0; k < 4; k++) {
                        dp[1][i] = Math.min(dp[1][i], dp[k][i - 1] + 1);
                    }
                    dp[2][i] = dp[2][i - 1] + 1;
                } else {
                    dp[1][i] = 1;
                }
            } else if (first[i] == '0' && second[i] == '1') {
                if (i > 0) {
                    dp[2][i] = Math.min(
                            dp[2][i - 1],
                            dp[3][i - 1]
                    );
                    for (int k = 0; k < 4; k++) {
                        dp[2][i] = Math.min(dp[2][i], dp[k][i - 1] + 1);
                    }
                    dp[1][i] = dp[1][i - 1] + 1;
                } else {
                    dp[2][i] = 1;
                }
            } else {
                if (i > 0) {
                    dp[3][i] = Math.min(
                            Math.min(dp[0][i - 1] + 2, dp[3][i - 1]),
                            Math.min(dp[1][i - 1], dp[2][i - 1]) + 1
                    );
                    for (int k = 0; k < 4; k++) {
                        dp[0][i] = Math.min(dp[0][i], dp[k][i - 1] + 1);
                    }
                } else {
                    dp[3][i] = 2;
                    dp[0][i] = 1;
                }
            }
        }
//        for (int i = 0; i < 4; i++) out.println(Arrays.toString(dp[i]));
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) answer = Math.min(answer, dp[i][counter - 1]);
        out.println(answer);
    }
}

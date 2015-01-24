package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int div = in.nextInt();
        int mod = in.nextInt();
        int[][][] dp = new int[length + 1][div][2];
        dp[0][0][0] = 1 % mod;
        int currentPower = 1 % div;
        for (int len = 0; len < length; ++len) {
            for (int rem = 0; rem < div; ++rem) {
                for (int digit = 0; digit < 10; ++digit) {
                    for (int state = 0; state < 2; ++state) {
                        if (digit == 0 && len == length - 1) continue;
                        int nextRem = (rem + currentPower * digit) % div;
                        int nextState = (state == 1 || (nextRem == 0 && digit != 0)) ? 1 : 0;
                        dp[len + 1][nextRem][nextState] = (dp[len + 1][nextRem][nextState] + dp[len][rem][state]) % mod;
                    }
                }
            }
            currentPower = currentPower * 10 % div;
        }
        int answer = 0;
        for (int rem = 0; rem < div; ++rem) {
            answer = (answer + dp[length][rem][1]) % mod;
        }
        out.println(answer);
    }
}

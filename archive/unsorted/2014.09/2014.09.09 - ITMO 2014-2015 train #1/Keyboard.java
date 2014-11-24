package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Keyboard {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int letters = in.nextInt();
        int keys = in.nextInt();
        int[] frequencies = new int[letters];
        for (int i = 0; i < letters; ++i) frequencies[i] = in.nextInt();
        // [current letter][current key][on current key]
        long[][][] dp = new long[letters + 1][keys + 1][letters + 1];
        for (long[][] dd : dp) for (long[] d : dd) Arrays.fill(d, Long.MAX_VALUE);
        dp[1][1][0] = frequencies[0];
        long[][] min = new long[letters + 1][keys + 1];
        for (long[] d : min) Arrays.fill(d, Long.MAX_VALUE);
        min[1][1] = dp[1][1][0];
        for (int currentLetter = 2; currentLetter <= letters; ++currentLetter) {
            for (int currentKey = 1; currentKey <= keys; ++currentKey) {
                for (int have = 0; have < currentLetter; have++) {
                    if (have == 0) {
                        if (min[currentLetter - 1][currentKey - 1] == Long.MAX_VALUE) continue;
                        dp[currentLetter][currentKey][have] = Math.min(dp[currentLetter][currentKey][have],
                                frequencies[currentLetter - 1] +
                                        min[currentLetter - 1][currentKey - 1]);
                    } else {
                        if (dp[currentLetter - 1][currentKey][have - 1] == Long.MAX_VALUE) continue;
                        dp[currentLetter][currentKey][have] = Math.min(dp[currentLetter][currentKey][have],
                                (have + 1L) * frequencies[currentLetter - 1] +
                                        dp[currentLetter - 1][currentKey][have - 1]
                        );
                    }
                    min[currentLetter][currentKey] = Math.min(min[currentLetter][currentKey],
                            dp[currentLetter][currentKey][have]);
                }
            }
        }
        long answer = Long.MAX_VALUE;
        for (long a : min[letters]) answer = Math.min(answer, a);
        out.println(answer);
    }
}

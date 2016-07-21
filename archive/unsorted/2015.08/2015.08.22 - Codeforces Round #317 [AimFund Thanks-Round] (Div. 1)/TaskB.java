package tasks;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int k = in.nextInt();
        int[] array = in.nextIntArray(counter);
        ArrayUtils.sort(array);
        long sum = 0;
        for (int i = 1; i < counter; ++i) {
            sum += array[i] - array[i - 1];
        }
        int big = counter % k;
        int small = k - big;
        int bigLength = (counter + k - 1) / k;
        int smallLength = counter / k;
        long[][] dp = new long[big + 1][small + 1];
        for (int x = 0; x <= big; ++x) {
            for (int y = 0; y <= small; ++y) {
                int offset = x * bigLength + y * smallLength;
                if (0 < offset && offset < counter) {
                    long val = dp[x][y] + (array[offset] - array[offset - 1]);
                    if (x < big) {
                        dp[x + 1][y] = Math.max(dp[x + 1][y], val);
                    }
                    if (y < small) {
                        dp[x][y + 1] = Math.max(dp[x][y + 1], val);
                    }
                }
            }
        }
        out.println(sum - dp[big][small]);
    }
}

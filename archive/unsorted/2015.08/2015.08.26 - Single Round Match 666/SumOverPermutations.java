package tasks;

import java.util.Arrays;

public class SumOverPermutations {
    private static final int MODULO = 1_000_000_000 + 7;
    public int findSum(int n) {
        long[] mult = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            mult[i] = n;
            for (int j = 0; j < i - 1; ++j) {
                mult[i] = (mult[i] * (n - 2)) % MODULO;
            }
            for (int j = 0; j < 2 * i - 1; ++j) {
                mult[i] = (mult[i] * (n - 1)) % MODULO;
            }
        }
        long[][] ways = new long[n + 2][n + 1]; // [summands][num]
        Arrays.fill(ways[0], 1);
        long[][] qty = new long[n + 2][n + 1];
        long answer = 0;
        for (int i = 1; i <= n; ++i) {

            answer += qty[i + 1][n] * mult[i] % MODULO;
        }
        return (int) (answer % MODULO);
    }
}

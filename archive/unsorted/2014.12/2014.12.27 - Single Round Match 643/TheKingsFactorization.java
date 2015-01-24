package tasks;

import java.util.Arrays;

public class TheKingsFactorization {
    public long[] getVector(long N, long[] primes) {
        long[] result = new long[primes.length * 2 + 1];
        Arrays.fill(result, Long.MAX_VALUE);
        int cnt = 0;
        for (long p : primes) {
            result[cnt++] = p;
            N /= p;
        }
        for (int d = 2; d < 2e6; ++d) {
            while (N % d == 0) {
                result[cnt++] = d;
                N /= d;
            }
        }
        if (N != 1) {
            result[cnt++] = N;
        }
        Arrays.sort(result);
        return Arrays.copyOf(result, cnt);
    }
}

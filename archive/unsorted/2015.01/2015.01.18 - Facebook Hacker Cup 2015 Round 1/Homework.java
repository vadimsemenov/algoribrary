package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Homework {
    private static final int MAX_NUMBER = 10_000_000;
    private static final int PRIMES_COUNTER = 664579;
    private static final int[] primacity = new int[MAX_NUMBER + 1];

    static {
        long start = System.currentTimeMillis();
        boolean[] isPrime = new boolean[MAX_NUMBER + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        int[] primes = new int[PRIMES_COUNTER + 1];
        int ptr = 0;
        for (int i = 2; i <= MAX_NUMBER; ++i) {
            if (isPrime[i]) {
                primes[ptr++] = i;
                if (i * 1L * i <= MAX_NUMBER) {
                    for (int d = i * i; d <= MAX_NUMBER; d += i) {
                        isPrime[d] = false;
                    }
                }
            }
        }

        for (int i = 2; i <= MAX_NUMBER; ++i) {
            if (i <= 30) System.err.println(primacity[i - 1]);
            int number = i;
            for (int p : primes) {
                if (p * p > number) break;
                if (number % p == 0) {
                    primacity[i]++;
                    while (number % p == 0) {
                        number /= p;
                    }
                }
            }
            if (number != 1) primacity[i]++;
        }
        System.err.println(System.currentTimeMillis() - start);
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int from = in.nextInt();
        int to = in.nextInt();
        assert 2 <= from && from <= to && to <= MAX_NUMBER : from + " " + to;
        int k = in.nextInt();
        int answer = 0;
        for (int i = from; i <= to; ++i) {
            if (primacity[i] == k) answer++;
        }
        out.println("Case #" + testNumber + ": " + answer);
    }
}

package tasks;


import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskD {
    private static final int MAX = 1_000_000;
    private static final boolean[] NOT_PRIME = new boolean[MAX];
    private static final int[] PRIMES;

    static {
        int cnt = 0;
        NOT_PRIME[0] = NOT_PRIME[1] = true;
        for (int p = 2; p < MAX; ++p) {
            if (!NOT_PRIME[p]) {
                cnt++;
                if (p * 1L * p < MAX) {
                    for (int q = p * p; q < MAX; q += p) {
                        NOT_PRIME[q] = true;
                    }
                }
            }
        }
        PRIMES = new int[cnt];
        cnt = 0;
        for (int p = 2; p < MAX; ++p) {
            if (!NOT_PRIME[p]) {
                PRIMES[cnt++] = p;
            }
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long A = in.nextLong();
        out.println(rec(0, A));
    }

    private int rec(int from, long num) {
        if (num == 1) {
            return 1;
        }
        int retval = 0;
        for (int i = from; i < PRIMES.length; ++i) {
            long divisor = PRIMES[i];
            if (divisor * divisor + 1 > num) {
                break;
            }
            while (divisor < num) {
                if (num % (divisor + 1) == 0) {
                    retval += rec(i + 1, num / (divisor + 1));
                }
                divisor *= PRIMES[i];
            }
        }
        if (num - 1 >= PRIMES[from] && isPrime(num - 1)) {
            ++retval;
        }
        return retval;
    }

    private boolean isPrime(long number) {
        if (number < MAX) {
            return !NOT_PRIME[(int) number];
        }
        return BigInteger.valueOf(number).isProbablePrime(10);
    }
}

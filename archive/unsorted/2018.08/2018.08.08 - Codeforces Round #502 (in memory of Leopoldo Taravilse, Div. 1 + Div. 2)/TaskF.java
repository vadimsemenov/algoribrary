package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

public final class TaskF {
    private int n, a, b, c, d;

    public void solve(int __, InputReader in, PrintWriter out) {
        n = in.nextInt();
        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();
        d = in.nextInt();
        int sqrt = 3;
        while (sqrt * sqrt < n) {
            sqrt++;
        }
        boolean[] isPrime = new boolean[sqrt + 1];
        Arrays.fill(isPrime, 2, isPrime.length, true);
        int primesQty = 0;
        for (int p = 2; p < isPrime.length; ++p) {
            if (isPrime[p]) {
                primesQty++;
                for (int np = p * p; np < isPrime.length; np += p) {
                    isPrime[np] = false;
                }
            }
        }
        int[] primes = new int[primesQty];
        primesQty = 0;
        int answer = 0;
        for (int prime = 2; prime < isPrime.length; ++prime) {
            if (isPrime[prime]) {
                primes[primesQty++] = prime;
                answer += g(prime);
            }
        }
        for (int block = 1; block <= n / sqrt; ++block) {
            Arrays.fill(isPrime, true);
            int from = block * isPrime.length;
            int to = Math.min(n + 1, from + isPrime.length);
            for (int prime : primes) {
                for (int np = (from + prime - 1) / prime * prime; np < to; np += prime) {
                    isPrime[np - from] = false;
                }
            }
            for (int prime = from; prime < to; ++prime) {
                if (isPrime[prime - from]) {
                    answer += g(prime);
                }
            }
        }
        out.println(Integer.toUnsignedString(answer));
    }

    private int g(int x) {
        int result = 0;
        int fx = f(x);
        for (int pow = x; ; pow *= x) {
            result += n / pow * fx;
            if (n / x < pow) break;
        }
        return result;
    }

    private int f(int x) {
        return d + x * (c + x * (b + x * a));
    }
}

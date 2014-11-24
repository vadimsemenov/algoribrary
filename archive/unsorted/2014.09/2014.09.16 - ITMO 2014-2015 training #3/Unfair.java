package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Unfair {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        boolean[] notPrime = new boolean[2_000_000];
        notPrime[0] = notPrime[1] = true;
        int cnt = 0;
        for (int p = 2; p < notPrime.length; ++p) {
            if (!notPrime[p]) {
                cnt++;
                if (p * 1L * p < notPrime.length) {
                    for (int d = p * p; d < notPrime.length; d += p) {
                        notPrime[d] = true;
                    }
                }
            }
        }
        int[] primes = new int[cnt];
        cnt = 0;
        for (int i = 0; i < notPrime.length; ++i) if (!notPrime[i]) {
            primes[cnt++] = i;
        }
        System.err.println(cnt);

        long number = in.nextLong();
        int res = 0;
        for (int i = 0; i < primes.length; ++i) {
            while (number % primes[i] == 0) {
                res ^= i + 1;
                number /= primes[i];
            }
        }
        if (number != 1) {
            res = 1;
        }
        out.println(res != 0 ? "David" : "Vasya");
    }
}

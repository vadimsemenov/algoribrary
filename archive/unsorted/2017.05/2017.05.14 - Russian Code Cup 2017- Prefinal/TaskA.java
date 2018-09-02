package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TaskA {
    static final int UPTO = 100_000;
    static final boolean[] isPrime = new boolean[UPTO];
    static final int[] primes;

    static {
        int qty = 0;
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int p = 2; p < UPTO; ++p) {
            if (isPrime[p]) {
                qty++;
                if ((long) p * p < UPTO) {
                    for (int d = p * p; d < UPTO; d += p) {
                        isPrime[d] = false;
                    }
                }
            }
        }
        primes = new int[qty];
        qty = 0;
        for (int p = 2; p < UPTO; ++p) {
            if (isPrime[p]) {
                primes[qty++] = p;
            }
        }
    }

    static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b > 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    public void solve(int __, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int gcd = gcd(a, b);
        a /= gcd;
        b /= gcd;
        List<Integer> result = new ArrayList<>();
        result.addAll(transform(a));
        result.addAll(transform(b));
        long aa = 1;
        long bb = 1;
        for (int t : result) bb *= t;
        for (int mask = 0; mask < (1 << result.size()); ++mask) {
            long aaa = 1;
            long bbb = 1;
            for (int i = 0; i < result.size(); ++i) {
                if (((mask >>> i) & 1) == 0) {
                    aaa *= result.get(i);
                } else {
                    bbb *= result.get(i);
                }
            }
            if (aaa + bbb < aa + bb) {
                aa = aaa;
                bb = bbb;
            }
        }
        out.println(aa + " " + bb);
    }

    private List<Integer> transform(int a) {
        List<Integer> result = new ArrayList<>();
        for (int p : primes) {
            if (p * p > a) break;
            boolean need = false;
            while (a % p == 0) {
                need = !need;
                a /= p;
            }
            if (need) {
                result.add(p);
            }
        }
        if (a != 1) {
            result.add(a);
        }
        return result;
    }
}

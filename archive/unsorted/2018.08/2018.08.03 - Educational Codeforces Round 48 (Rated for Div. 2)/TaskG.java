package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskG {
    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        long targetGcd = in.nextLong();
        long targetLcm = in.nextLong();
        long[] rangs = in.nextLongArray(qty);
        if (targetLcm % targetGcd != 0) {
            out.println(0);
            return;
        }
        long[] primes = getPrimes(targetLcm, rangs);
        int[] lcmFactorization = factorize(targetLcm, primes);
        int[] gcdFactorization = factorize(targetGcd, primes);
        int primesQty = 0;
        for (int i = 0; i < primes.length; ++i) {
            if (lcmFactorization[i] > gcdFactorization[i]) {
                primes[primesQty] = primes[i];
                lcmFactorization[primesQty] = lcmFactorization[i];
                gcdFactorization[primesQty] = gcdFactorization[i];
                ++primesQty;
            }
        }
        if (primesQty < primes.length) {
            primes = Arrays.copyOfRange(primes, 0, primesQty);
            lcmFactorization = Arrays.copyOfRange(lcmFactorization, 0, primesQty);
            gcdFactorization = Arrays.copyOfRange(gcdFactorization, 0, primesQty);
        }
        boolean[] possibleGcd = new boolean[qty];
        boolean[] possibleLcm = new boolean[qty];
        int[] maskGcd = new int[qty];
        int[] maskLcm = new int[qty];
        for (int i = 0; i < qty; ++i) {
            long rang = rangs[i];
            possibleGcd[i] = rang % targetGcd == 0;
            possibleLcm[i] = targetLcm % rang == 0;
            if (!possibleGcd[i] && !possibleLcm[i]) continue;
            int[] factorization = factorize(rang, primes);
            for (int j = 0; j < primes.length; ++j) {
                if (factorization[j] > gcdFactorization[j]) {
                    maskGcd[i] |= 1 << j;
                }
                if (factorization[j] < lcmFactorization[j]) {
                    maskLcm[i] |= 1 << j;
                }
            }
        }
        int totalMask = (1 << primes.length) - 1;
        int[] count = new int[totalMask + 1];
        for (int i = 0; i < qty; ++i) {
            if (possibleGcd[i]) {
                count[maskGcd[i]]++;
            }
        }
        for (int mask = totalMask; mask > 0; --mask) {
            for (int subMask = (mask - 1) & mask; subMask > 0; subMask = (subMask - 1) & mask) {
                count[mask] += count[subMask];
            }
            count[mask] += count[0];
        }
        long answer = 0;
        for (int i = 0; i < qty; ++i) {
            if (possibleLcm[i]) {
                answer += count[totalMask ^ maskLcm[i]];
            }
        }
        out.println(answer);
    }

    private int[] factorize(long number, long[] primes) {
        int[] powers = new int[primes.length];
        for (int i = 0; i < primes.length; ++i) {
            while (number % primes[i] == 0) {
                powers[i]++;
                number /= primes[i];
            }
        }
        return powers;
    }

    private long[] getPrimes(long number, long[] rangs) {
        List<Long> primes = new ArrayList<>();
        for (int p = 2; p <= Math.min(1_000_000, 10 + Math.sqrt(number)); ++p) {
            if (number % p == 0) {
                primes.add((long) p);
                while (number % p == 0) {
                    number /= p;
                }
            }
        }
        if (number > 1) {
            primes.add(number);
            for (long rang : rangs) {
                long gcd = gcd(rang, number);
                if (gcd != 1 && gcd != number) {
                    long div = number / gcd;
                    primes.set(primes.size() - 1, Math.min(gcd, div));
                    if (gcd != div) {
                        primes.add(Math.max(gcd, div));
                    }
                }
            }
        }
        long[] result = new long[primes.size()];
        for (int i = 0; i < primes.size(); ++i) {
            result[i] = primes.get(i);
        }
        return result;
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}

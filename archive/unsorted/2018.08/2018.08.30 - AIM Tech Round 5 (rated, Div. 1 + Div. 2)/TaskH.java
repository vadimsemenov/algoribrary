package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskH {
    private static final int MAX_VALUE = 5032107;
    private static final int[] PRIME_DIVISOR = new int[MAX_VALUE + 1];
    private static final int[] PRIMES_QTY = new int[MAX_VALUE + 1];
    private static final int MAX_QTY;
    static {
        for (int d = 0; d <= MAX_VALUE; ++d) {
            PRIME_DIVISOR[d] = d;
        }
        for (int prime = 2; prime <= MAX_VALUE / prime; ++prime) {
            if (PRIME_DIVISOR[prime] == prime) {
                for (int number = prime * prime; number <= MAX_VALUE; number += prime) {
                    if (PRIME_DIVISOR[number] == number) {
                        PRIME_DIVISOR[number] = prime;
                    }
                }
            }
        }
        Arrays.fill(PRIMES_QTY, 1);
        PRIMES_QTY[0] = 0;
        PRIMES_QTY[1] = 0;
        int max = 1;
        for (int number = 4; number <= MAX_VALUE; ++number) {
            int prime = PRIME_DIVISOR[number];
            int divisor = number / prime;
            PRIMES_QTY[number] = PRIME_DIVISOR[divisor] == prime ?
                    PRIMES_QTY[divisor / prime] : (PRIMES_QTY[divisor] + 1);
            max = Math.max(max, PRIMES_QTY[number]);
        }
        MAX_QTY = max;
    }

    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int queries = in.nextInt();
        // aux[divisor][divisorsCount] = max i: a[i] % divisor == 0 && DIVISORS_QTY[a[i] / divisor] == divisorsCount
        int[][] aux = new int[MAX_VALUE + 1][MAX_QTY + 1];
        // maxLeft[answer][right] = max l: answer(left, right) == answer
        int[][] maxLeft = new int[length + 1][2 * MAX_QTY + 1];
        for (int i = 1; i <= length; ++i) {
            System.arraycopy(maxLeft[i - 1], 0, maxLeft[i], 0, maxLeft[i].length);
            int number = in.nextInt();
            int[] factorization = factorize(number);
            for (int mask = (1 << PRIMES_QTY[number]); mask --> 0; ) {
                int divisor = getDivisor(factorization, mask);
                int count = PRIMES_QTY[number] - PRIMES_QTY[divisor];
                for (int k = 0; k <= MAX_QTY; ++k) {
                    maxLeft[i][k + count] = Math.max(maxLeft[i][k + count], aux[divisor][k]);
                }
            }
            for (int mask = (1 << PRIMES_QTY[number]); mask --> 0; ) {
                int divisor = getDivisor(factorization, mask);
                int count = PRIMES_QTY[number] - PRIMES_QTY[divisor];
                aux[divisor][count] = i;
            }
        }
        while (queries --> 0) {
            int left = in.nextInt();
            int right = in.nextInt();
            int answer = 0;
            while (maxLeft[right][answer] < left) {
                ++answer;
            }
            out.println(answer);
        }
    }

    private int getDivisor(int[] factorization, int mask) {
        int divisor = 1;
        for (int bit = 32 - Integer.numberOfLeadingZeros(mask); bit >= 0; --bit) {
            if (((mask >>> bit) & 1) == 1) {
                divisor *= factorization[bit];
            }
        }
        return divisor;
    }

    private int[] factorize(int number) {
        int[] factorization = new int[PRIMES_QTY[number]];
        int ptr = 0;
        while (number > 1) {
            int prime = PRIME_DIVISOR[number];
            int power = 0;
            while (number % prime == 0) {
                number /= prime;
                power++;
            }
            if (power % 2 != 0) {
                factorization[ptr++] = prime;
            }
        }
        assert ptr == factorization.length;
        return factorization;
    }
}

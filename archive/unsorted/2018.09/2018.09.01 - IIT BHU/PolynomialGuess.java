package tasks;

import java.math.BigInteger;
import java.util.Arrays;

public class PolynomialGuess {
    public int findValue(int[] p) {
        long[] numerators = new long[p.length];
        long[] denominators = new long[p.length];
        Arrays.fill(numerators, 1);
        Arrays.fill(denominators, 1);
        int x = 6;
        for (int i = 0; i < p.length; ++i) {
            for (int j = 0; j < p.length; ++j) {
                if (i != j) {
                    numerators[i] *= (x - j);
                    denominators[i] *= (i - j);
                }
            }
        }
        long lcm = lcm(denominators);
        long answer = 0;
        for (int i = 0; i < p.length; ++i) {
            answer += numerators[i] * p[i] * (lcm / denominators[i]);
        }
        return (int) (answer / lcm);
    }

    private long lcm(long[] numbers) {
        BigInteger lcm = BigInteger.ONE;
        for (long number : numbers) {
            BigInteger d = BigInteger.valueOf(number).abs();
            lcm = lcm.multiply(d).divide(lcm.gcd(d));
        }
        return lcm.longValue();
    }
}

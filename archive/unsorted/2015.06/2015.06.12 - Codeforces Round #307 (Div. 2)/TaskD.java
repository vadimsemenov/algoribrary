package tasks;



import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskD {
    private long ONE;
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        long counter = in.nextLong();
        long target = in.nextLong();
        int bits = in.nextInt();
        int modulo = in.nextInt();
        ONE = 1 % modulo;
        long answer = ONE;
        long total = power(2 % modulo, counter, modulo);
        long[] fibs = power(new long[]{ONE, ONE, ONE, 0}, counter + 1, modulo);
        long zero = fibs[0];
        long one = (total + (modulo - zero)) % modulo;
        for (int bit = 0; bit < bits; ++bit) {
            if ((target >>> bit & 1) == 1) {
                target -= (1L << bit);
                answer = (answer * one) % modulo;
            } else {
                answer = (answer * zero) % modulo;
            }
        }
        out.println(target == 0 ? answer : 0);
    }

    private long power(long base, long power, int modulo) {
        long result = ONE;
        while (power > 0) {
            if ((power & 1) == 1) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            power >>>= 1;
        }
        return result;
    }

    private long[] power(long[] base, long power, int modulo) {
        long[] result = new long[]{ONE, 0, 0, ONE};
        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiply(result, base, modulo);
            }
            base = multiply(base, base, modulo);
            power >>>= 1;
        }
        return result;
    }

    private long[] multiply(long[] first, long[] second, int modulo) {
        long[] result = new long[4];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < 2; ++k) {
                    result[2 * i + j] = (result[2 * i + j] + first[i * 2 + k] * second[k * 2 + j]) % modulo;
                }
            }
        }
        return result;
    }
}

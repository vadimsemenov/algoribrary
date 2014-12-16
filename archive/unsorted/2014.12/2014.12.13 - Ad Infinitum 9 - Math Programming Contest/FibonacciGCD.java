package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class FibonacciGCD {
    private static final int MODULO = 1_000_000_007;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long gcd = -1;
        for (int i = 0; i < counter; ++i) {
            long a = in.nextLong();
            if (gcd == -1) {
                gcd = a;
            } else {
                gcd = gcd(gcd, a);
            }
        }
        long[] fibs = new long[]{1, 1, 1, 0};
        fibs = binaryPow(fibs, gcd);
        System.err.println(Arrays.toString(fibs));
        out.println(fibs[1]);
    }

    private long[] binaryPow(long[] base, long pow) {
        long[] result = new long[]{1, 0, 0, 1};
        while (pow > 0) {
            if ((pow & 1) == 1) {
                result = multiply(result, base);
            }
            base = multiply(base, base);
            pow >>= 1;
        }
        return result;
    }

    private long[] multiply(long[] a, long[] b) {
        long[] result = new long[4];
        result[0] = (a[0] * b[0] + a[1] * b[2]) % MODULO;
        result[1] = (a[0] * b[1] + a[1] * b[3]) % MODULO;
        result[2] = (a[2] * b[0] + a[3] * b[2]) % MODULO;
        result[3] = (a[2] * b[1] + a[3] * b[3]) % MODULO;
        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}

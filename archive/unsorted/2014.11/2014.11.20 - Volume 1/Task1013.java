package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Task1013 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long digits = in.nextLong();
        long base = in.nextLong();
        long modulo = in.nextLong();
        long[] matrix = new long[]{0, 1, 1, 1};
        matrix = pow(matrix, digits, modulo);
    }

    private long[] pow(long[] matrix, long pow, long modulo) {
        long[] result = new long[]{1, 0, 0, 1};
        while (pow > 0) {
            if ((pow & 1) == 1) {
                multiply(result, matrix, modulo);
            }
            pow >>= 1;
            multiply(matrix, matrix, modulo);
        }
        return result;
    }

    private void multiply(long[] a, long[] b, long modulo) {
        long na = multiply(a[0], b[0], modulo);
    }

    private long multiply(long a, long b, long modulo) {
        final long base = 1_000_000_000;
        long aa = a / base;
        long ab = a % base;
        long ba = b / base;
        long bb = b % base;
        return aa;
    }

}

/*
(a * 10^9 + b) * (c * 10^9 + d) = a * c * 10^18 + (a * d + b * c) * 10^9 + b * d
 */
/*
A = base * B'
B = base * (A' + B')
(A', B') * ((0, 1), (1, 1))
 */

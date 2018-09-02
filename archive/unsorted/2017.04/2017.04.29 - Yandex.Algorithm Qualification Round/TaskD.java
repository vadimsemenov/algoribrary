package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int[] powers = new int[64];
        long n = in.nextLong();
        for (int k = 1; ; ++k) {
            int bits = Long.bitCount(k * n);
            if (bits <= k) {
                int finalK = k;
                Arrays.setAll(powers, bit -> (int) ((finalK * n) >>> bit & 1));
                for (int bit = 63; bit > 0; --bit) {
                    int sub = Math.min(k - bits, powers[bit]);
                    bits += sub;
                    powers[bit] -= sub;
                    powers[bit - 1] += sub * 2;
                }
                out.print(k);
                for (int power = 0; power < powers.length; ++power) {
                    for (int t = 0; t < powers[power]; ++t) {
                        out.print(' ');
                        out.print(power);
                    }
                }
                out.println();
                return;
            }
        }
    }
}

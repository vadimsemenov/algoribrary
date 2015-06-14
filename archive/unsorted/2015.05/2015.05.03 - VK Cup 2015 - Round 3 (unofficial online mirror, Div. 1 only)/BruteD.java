package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class BruteD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        final int upTo = 100;
        int[] answer = new int[upTo];
        for (int x = 1; x < upTo; ++x) {
            int J = 0;
            for (int k = 1; k <= x; ++k) {
                if (x % k == 0 && BigInteger.valueOf(k).gcd(BigInteger.valueOf(x / k)).longValue() == 1) {
                    J += k;
                }
            }
            if (J < upTo) {
                answer[J]++;
            }
        }
        for (int i = 0; i < upTo; ++i) {
            out.print(answer[i] + ", ");
        }
        for (int i = 1; i < upTo; ++i) {
            out.println(i + " " + answer[i]);
        }
    }
}

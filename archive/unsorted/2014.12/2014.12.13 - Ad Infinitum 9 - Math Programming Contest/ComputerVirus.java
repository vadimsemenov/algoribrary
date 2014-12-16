package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ComputerVirus {
    private static final int MODULO = 1_000_000_007;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long time = in.nextLong() - 1;
        long answer = counter % 2 == 0 ? 1 : -1;
        for (int i = counter, mul = 1; i > 0; --i, mul = -mul) {
            answer += mul * binaryPow(2, i) * (binaryPow(i, time + 1) - 1) % MODULO * binaryPow(i - 1, MODULO - 2) %
                    MODULO;
        }
        out.println((answer + MODULO) % MODULO);
    }

    private long binaryPow(long base, long pow) {
        long result = 1;
        while (pow > 0) {
            if ((pow & 1) == 1) {
                result = result * base % MODULO;
            }
            base = base * base % MODULO;
            pow >>>= 1;
        }
        return result;
    }
}

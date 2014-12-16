package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ManasaAndCalculations {
    private static final int MODULO = 1_000_000_007;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int primes = in.nextInt();
        int gcd = 1;
        int sum = 1;
        for (int i = 0; i < primes; ++i) {
            int prime = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            gcd = (int) (gcd * 1L * binaryPow(prime, a) % MODULO);
            if (b > a) sum = (int) (sum * (binaryPow(prime, b - a) + 1L) % MODULO);
        }
        out.println(gcd * Math.max(2L, sum) % MODULO);
    }

    private int binaryPow(int base, int pow) {
        int result = 1;
        while (pow > 0) {
            if ((pow & 1) == 1) {
                result = (int) (result * 1L * base % MODULO);
            }
            base = (int) (base * 1L * base % MODULO);
            pow >>= 1;
        }
        return result;
    }
}

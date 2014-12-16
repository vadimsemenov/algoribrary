package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class EulersCriterion {
    public static void main(String[] args) {
        boolean[] isPrime = new boolean[1000];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int p = 2; p < isPrime.length; ++p) {
            if (isPrime[p]) {
                for (int d = p * p; d < isPrime.length; d += p) {
                    isPrime[d] = false;
                }
                boolean[] isSqr = new boolean[p];
                for (int i = 0; i < p; ++i) {
                    isSqr[i * i % p] = true;
                }
                for (int i = 0; i < p; ++i) {
                    String res = solve(i, p);
                    if ((isSqr[i] && res.equals("NO")) || (!isSqr[i] && res.equals("YES"))) {
                        throw new AssertionError(i + " " + p);
                    }
                }
            }
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int mod = in.nextInt();
        out.println(solve(a, mod));
    }

    private static String solve(int a, int mod) {
        if (mod == 2) {
            return "YES";
        } else if (a == 0) {
            return "YES";
        } else {
            int l = binaryPow(a, (mod - 1) / 2, mod);
            if (l == 1) {
                return "YES";
            } else {
                return "NO";
            }
        }
    }

    private static int binaryPow(int base, int pow, int mod) {
        int result = 1;
        while (pow > 0) {
            if ((pow & 1) == 1) {
                result = (int) (result * 1L * base % mod);
            }
            base = (int) (base * 1L * base % mod);
            pow >>= 1;
        }
        return result;
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Cake {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        final boolean stress = false;
        if (!stress) {
            int a = in.nextInt();
            int b = in.nextInt();
            if (a < b) {
                int t = a;
                a = b;
                b = t;
            }
            out.println(solve(a, b));
        } else {
            final int M = 1000;
            for (int a = 1; a < M; ++a) {
                for (int b = 1; b <= a; ++b) {
                    long bf = bf(a, b);
                    long my = solve(a, b);
                    if (bf != my) {
                        out.println(a + " " + b);
                        out.println(bf + " " + my);
                        return;
                    }
                }
            }
        }
    }

    private long bf(int a, int b) {
        long result = 0;
        for (int d = 1; d <= a; ++d) {
            if (a % d != 0 && a % d == b % d)
                result++;
        }
        return result;
    }

    private long solve(int a, int b) {
        long result = 0;
        if (a - b == 1) return 0;
        if (a == b) {
            result = 1;
            for (int p = 2; p <= b; ++p) {
                if (b % p == 0) {
                    int cnt = 0;
                    while (b % p == 0) {
                        b /= p;
                        cnt++;
                    }
                    result *= cnt + 1;
                }
            }
            result = a - result;
        } else {
            int n = a - b;
            for (int d = 1; d * d <= n; ++d) {
                if (n % d == 0 && a % d != 0) result++;
                if (d * d != n) {
                    int dd = n / d;
                    if (n % dd == 0 && a % dd != 0) result++;
                }
            }
        }
        return (result);
    }
}

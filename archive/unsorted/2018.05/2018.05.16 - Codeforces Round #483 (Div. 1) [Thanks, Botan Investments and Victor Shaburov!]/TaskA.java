package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        long numerator = in.nextLong();
        long denominator = in.nextLong();
        long base = in.nextLong();
        denominator /= gcd(numerator, denominator);
        do {
            base = gcd(denominator, base);
            denominator /= base;
        } while (base > 1);
        out.println(denominator == 1 ? "Finite" : "Infinite");
    }

    private static long gcd(long a, long b) {
        while (a != 0) {
            long c = b % a;
            b = a;
            a = c;
        }
        return b;
    }
}

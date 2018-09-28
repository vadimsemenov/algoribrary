package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        long a = in.nextLong();
        long b = in.nextLong();
        long x = in.nextLong();
        long y = in.nextLong();
        long gcd = gcd(x, y);
        x /= gcd;
        y /= gcd;
        out.println(Math.min(a / x, b / y));
    }

    private long gcd(long x, long y) {
        while (x > 0) {
            long t = y % x;
            y = x;
            x = t;
        }
        return y;
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int d = in.nextInt();
        long p = lcm(a, c);
        long q = gcd(b, d);
        long g = gcd(p, q);
        out.println((p / g) + " " + (q / g));
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}

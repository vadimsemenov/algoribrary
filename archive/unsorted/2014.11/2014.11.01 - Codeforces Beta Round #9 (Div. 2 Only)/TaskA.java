package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int numerator = 6 - Math.max(in.nextInt(), in.nextInt()) + 1;
        int denominator = 6;
        int d = (int) BigInteger.valueOf(numerator).gcd(BigInteger.valueOf(denominator)).longValue();
        out.println(numerator / d + "/" + denominator / d);
    }
}

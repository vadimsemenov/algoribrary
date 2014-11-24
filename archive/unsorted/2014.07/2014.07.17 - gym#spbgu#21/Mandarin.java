package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Mandarin {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int m = in.nextInt();
        BigInteger x = BigInteger.ONE;
        for (int i = 2; i <= m; i++) {
            BigInteger tmp = BigInteger.valueOf(i);
            BigInteger d = x.gcd(tmp);
            x = x.multiply(tmp.divide(d));
        }
        out.println(x.subtract(BigInteger.ONE));
    }
}

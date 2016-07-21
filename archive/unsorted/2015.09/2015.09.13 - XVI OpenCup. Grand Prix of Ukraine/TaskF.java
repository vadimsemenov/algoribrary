package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskF {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int first = in.nextInt();
        int last = in.nextInt();
        if (first == 1 && last == 1) {
            out.println(0);
            return;
        }
        if (last % 2 != 0 || last == 0) {
            out.println(-1);
            return;
        }
        BigInteger current = BigInteger.ONE;
        int n = 0;
        while (true) {
            String chars = current.toString();
            if (chars.charAt(0) == first + '0' && chars.charAt(chars.length() - 1) == last + '0') {
                out.println(n);
                return;
            }
            current = current.shiftLeft(1);
            ++n;
        }
    }
}

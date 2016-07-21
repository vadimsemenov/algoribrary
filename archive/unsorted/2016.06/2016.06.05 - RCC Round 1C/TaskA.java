package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            long a = in.nextInt();
            long b = in.nextInt();
            long c = in.nextInt();
            long cc = c * c;
            long target = a * b;
            long fst = target / cc * cc;
            long snd = (target + cc - 1) / cc * cc;
            if (target - fst < snd - target && fst > 0) {
                out.println(fst);
            } else {
                out.println(snd);
            }
        }
    }
}

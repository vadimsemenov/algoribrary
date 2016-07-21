package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskI {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        final int MAX = 1000_000_000;
        boolean[] can = new boolean[MAX];
        can[0] = can[1] = true;
        int mx = 1;
        long num = in.nextLong();
        for (int d = 2; ; ++d) {
            if (num % d == 0) {
                for (int i = Math.min(mx, MAX - d - 1); i >= 0; --i) {
                    can[i + d] |= can[i];
                }
                mx += d;
            }
            if (!can[d]) {
                out.println(d);
                return;
            }
        }
    }
}

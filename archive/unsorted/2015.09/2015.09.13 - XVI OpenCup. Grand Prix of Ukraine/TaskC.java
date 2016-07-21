package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int no = in.nextInt();
        final int MAX = 1_000_000;
        boolean[] notPrime = new boolean[MAX];
        notPrime[0] = notPrime[1] = true;
        for (int p = 2; p < MAX; ++p) if (!notPrime[p]) {
            long j = p * 1L * p;
            if (j < MAX) for (int d = (int) j; d < MAX; d += p) {
                notPrime[d] = true;
            }
        }
        for (int i = 0; i < MAX; ++i) {
            if (!notPrime[i]) {
                int another = 0;
                int current = i;
                while (current > 0) {
                    another *= 10;
                    another += current % 10;
                    current /= 10;
                }
                if (!notPrime[another] && i != another) {
                    --no;
                    if (no == 0) {
                        out.println(i);
                        return;
                    }
                }
            }
        }
        throw new AssertionError("WTF?");
    }
}

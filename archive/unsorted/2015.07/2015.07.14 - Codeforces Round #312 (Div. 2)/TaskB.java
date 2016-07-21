package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        final int MAX = 1_000_000;
        int[] left = new int[MAX + 1];
        int[] right = new int[MAX + 1];
        int[] qty = new int[MAX + 1];
        for (int i = 1; i <= counter; ++i) {
            int foo = in.nextInt();
            ++qty[foo];
            right[foo] = i;
            if (left[foo] == 0) {
                left[foo] = i;
            }
        }
        int best = 0;
        for (int i = 1; i <= MAX; ++i) {
            if (qty[best] < qty[i] || (qty[best] == qty[i] && right[best] - left[best] > right[i] - left[i])) {
                best = i;
            }
        }
        out.println(left[best] + " " + right[best]);
    }
}

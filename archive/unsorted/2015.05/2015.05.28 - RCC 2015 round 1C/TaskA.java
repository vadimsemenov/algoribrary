package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            int one = in.nextInt();
            int two = in.nextInt();
            int cost = in.nextInt();
            if (cost % 2 == 0) {
                if (2 * two + one >= cost) {
                    out.println("YES");
                } else {
                    out.println("NO");
                }
            } else {
                if (one == 0) {
                    out.println("NO");
                } else if (2 * two + one >= cost) {
                    out.println("YES");
                } else {
                    out.println("NO");
                }
            }
        }
    }
}

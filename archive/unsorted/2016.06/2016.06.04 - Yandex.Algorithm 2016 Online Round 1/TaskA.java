package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int p = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        if (b > p) {
            out.println(-1);
        } else {
            out.println(Math.max(p, a + b));
        }
    }
}

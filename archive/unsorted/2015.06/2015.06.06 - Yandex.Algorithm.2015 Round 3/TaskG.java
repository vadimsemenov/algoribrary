package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskG {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int h = in.nextInt();
        int l = in.nextInt();
        int h1 = in.nextInt();
        int h2 = in.nextInt();
        int l1 = in.nextInt();
        int l2 = in.nextInt();
        double alpha = Math.atan2(h, l);
        out.println(alpha * (h1 + h2) / Math.PI + (Math.PI / 2 - alpha) * (l1 + l2) / Math.PI);
    }
}

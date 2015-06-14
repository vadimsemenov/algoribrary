package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int cost = in.nextInt();
        int cash = in.nextInt();
        int counter = in.nextInt();
        out.println(Math.max(0, cost * (1L + counter) * counter / 2 - cash));
    }
}

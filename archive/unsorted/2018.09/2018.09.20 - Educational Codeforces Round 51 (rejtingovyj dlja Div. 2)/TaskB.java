package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        long left = in.nextLong();
        long right = in.nextLong();
        assert (right - left + 1) % 2 == 0;
        out.println("YES");
        for (long x = left; x < right; x += 2) {
            out.println(x + " " + (x + 1));
        }
    }
}

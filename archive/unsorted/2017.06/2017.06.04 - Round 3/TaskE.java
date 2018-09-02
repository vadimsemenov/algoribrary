package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int d = in.nextInt();
        int x = Math.abs(a - c);
        int y = Math.abs(b - d);
        int t = Math.min(x, y);
        if (x != t) {
            y = x;
            x = t;
        }
        int s = y - x;
        int ad = s % 2 == 0 ? 2 * s : 2 * (s - 1) + 1;
        out.println(2 * x + ad);
    }
}

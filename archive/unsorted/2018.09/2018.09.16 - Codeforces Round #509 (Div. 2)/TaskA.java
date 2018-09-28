package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int min = in.nextInt();
        int max = min;
        for (int i = 1; i < n; ++i) {
            int a = in.nextInt();
            max = Math.max(max, a);
            min = Math.min(min, a);
        }
        out.println(max - min + 1 - n);
    }
}

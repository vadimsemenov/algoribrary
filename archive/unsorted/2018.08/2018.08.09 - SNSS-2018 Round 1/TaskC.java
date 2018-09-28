package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int answer = n;
        for (int inc = 1; inc <= n; ++inc) {
            answer = Math.min(answer, Math.max(inc, (n + inc - 1) / inc));
        }
        out.println(answer);
    }
}

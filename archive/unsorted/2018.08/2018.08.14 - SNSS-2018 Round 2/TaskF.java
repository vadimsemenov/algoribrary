package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        long n = in.nextInt();
        long k = in.nextInt();
        long l = in.nextInt();
        out.println(k + n + n * l);
    }
}

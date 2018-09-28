package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        String p = in.next();
        String q = in.next();
        out.println(p.equals(q) ? p : "1");
    }
}

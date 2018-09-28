package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int limit = in.nextInt();
        long current = 0;
        for (int i = 0; i < count; ++i) {
            int qty = in.nextInt();
            long next = current + qty;
            long answer = next / limit - current / limit;
            current += qty;
            if (i > 0) {
                out.print(' ');
            }
            out.print(answer);
        }
    }
}

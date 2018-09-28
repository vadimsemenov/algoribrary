package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        long sum = (long) n * (n + 1L) / 2;
        for (int d = 2; d * d <= sum; ++d) {
            if (sum % d == 0) {
                out.println("Yes");
                out.println("1 " + d);
                out.print(n - 1);
                for (int i = 1; i <= n; ++i) {
                    if (i != d) {
                        out.print(" " + i);
                    }
                }
                return;
            }
        }
        out.println("No");
    }
}

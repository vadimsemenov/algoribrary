package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        for (int a = 1; a * a <= n; a++) if (n % a == 0) {
            int b = n / a;
            if (b - a == 1) {
                out.println(a + " " + b);
                return;
            }
        }
        out.println("-1 -1");
    }
}

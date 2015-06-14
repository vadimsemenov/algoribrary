package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int A = in.nextInt();
        int B = in.nextInt();
        int counter = in.nextInt();
        for (int i = 0; i < counter; ++i) {
            int l = in.nextInt();
            int cnt = in.nextInt();
            int max = in.nextInt();
            long left = l - 1;
            long right = cnt * 1L * max + 1;
            while (right - left > 1) {
                long r = (left + right) >>> 1;
                if (A + B * (r - 1) <= cnt &&
                        (A + B * 1L * (l - 1) + A + B * 1L * (r - 1)) * (r - l + 1) <= cnt * 2L * max) {
                     left = r;
                } else {
                    right = r;
                }
            }
            out.println(left == l - 1 ? -1 : left);
        }
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        if (k == 2) {
            if (n == 2) {
                out.println(2);
            } else {
                out.println(n / k + (n - 1) / k);
            }
        } else {
            int lower = n / k;
            int ad = (n - 1 + k - 1) / k;
            if (n - k * lower > 1) {
                ad = 1 + (n - 2 + k - 1) / k;
            }
            out.println(lower + ad);
        }
    }
}

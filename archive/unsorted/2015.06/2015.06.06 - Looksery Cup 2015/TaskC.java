package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    private static final String first = "Stannis";
    private static final String second = "Daenerys";

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int odd = 0;
        int even = 0;
        for (int i = 0; i < n; ++i) {
            if (in.nextInt() % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        int ff = (n - k + 1) / 2;
        int ss = (n - k) / 2;

        if (k == n) {
            out.println(odd % 2 == 0 ? second : first);
        } else if (ss >= odd) {
            out.println(second);
        } else if (k % 2 == 0 && ss >= even) {
            out.println(second);
        } else if (k % 2 == 1 && ff >= even) {
            out.println(first);
        } else if ((n - k) % 2 == 0) {
            out.println(second);
        } else {
            out.println(first);
        }
    }
}

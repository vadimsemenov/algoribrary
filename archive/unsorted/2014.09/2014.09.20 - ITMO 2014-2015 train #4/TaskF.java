package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskF {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int l = in.nextInt();
        int r = in.nextInt();
        out.println(get(r) - get(l - 1));
    }

    private int get(int r) {
        int ans = r;
        for (int i = 2; i <= ans; ++i) {
            int bad = ans / i;
            ans -= bad;
        }
        return ans;
    }
}

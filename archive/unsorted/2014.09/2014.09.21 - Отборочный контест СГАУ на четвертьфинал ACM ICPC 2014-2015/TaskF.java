package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskF {
    private static final String TAKE = "Take another envelope";
    private static final String STAY = "Stay with this envelope";
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int left = in.nextInt();
        int right = in.nextInt();
        int have = in.nextInt();
        if (have % 2 != 0) {
            out.println(TAKE);
        } else if (have > right) {
            out.println(STAY);
        } else if (have / 2 < left) {
            out.println(TAKE);
        } else  {
            out.println(TAKE);
        }
    }
}

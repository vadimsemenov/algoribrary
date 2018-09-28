package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] xs = in.nextIntArray(count);
        int[] ys = in.nextIntArray(count);
        int sum = 0;
        for (int i = 0; i < count; ++i) {
            sum += xs[i] - ys[i];
        }
        out.println(sum >= 0 ? "Yes" : "No");
    }
}

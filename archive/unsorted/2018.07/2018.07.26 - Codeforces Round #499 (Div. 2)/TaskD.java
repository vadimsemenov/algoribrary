package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int max = in.nextInt();
        int length = in.nextInt();
        boolean[] truth = new boolean[length];
        for (int i = 0; i < length; ++i) {
            out.println(max);
            out.flush();
            int result = in.nextInt();
            if (result == 0) {
                return;
            }
            truth[i] = result < 0;
        }
        int left = 0;
        int right = max + 1;
        int count = 0;
        while (right - left > 1) {
            int mid = (left + right) >>> 1;
            out.println(mid);
            out.flush();
            int result = in.nextInt();
            if (result == 0 || result == -2) {
                return;
            }
            if (!truth[count++ % length]) {
                result = -result;
            }
            if (result > 0) {
                right = mid;
            } else {
                left = mid;
            }
        }
        throw new RuntimeException();
    }
}

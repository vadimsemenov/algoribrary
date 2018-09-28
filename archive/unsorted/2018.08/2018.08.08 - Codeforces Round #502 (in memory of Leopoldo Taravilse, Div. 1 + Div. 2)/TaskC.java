package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int best = 1;
        int bestSum = length + 1;
        for (int n = 2; n < length; ++n) {
            int sum = n + (length + n - 1) / n;
            if (sum < bestSum) {
                best = n;
                bestSum = sum;
            }
        }
        int last = length + 1;
        int blockLength = (length + best - 1) / best;
        for (int i = 0; i < best; ++i) {
            int start = Math.max(1, last - blockLength);
            for (int num = start; num < last; ++num) {
                out.print(num + " ");
            }
            last = start;
        }
    }
}

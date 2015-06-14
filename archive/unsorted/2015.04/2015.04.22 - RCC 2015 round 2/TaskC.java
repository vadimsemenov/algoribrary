package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] lengths = new int[counter];
        for (int i = 0; i < counter; ++i) lengths[i] = in.nextInt();
        int maxIdx = 0;
        for (int i = 1; i < counter; ++i) {
            if (lengths[maxIdx] < lengths[i]) {
                maxIdx = i;
            }
        }
        if (maxIdx == 0) {
            out.println(lengths[0]);
        } else {
            double ans = lengths[maxIdx] / 2.0;
            long sum = 0;
            for (int i = 0; i < maxIdx; ++i) {
                if (2 * (lengths[i] + sum) >= lengths[maxIdx]) {
                    ans = Math.max(ans, lengths[i] - sum);
                }
                sum += lengths[i];
            }
            if (2 * sum >= lengths[maxIdx]) {
                out.println(ans);
            } else {
                out.println(lengths[maxIdx] - sum);
            }
        }
    }
}

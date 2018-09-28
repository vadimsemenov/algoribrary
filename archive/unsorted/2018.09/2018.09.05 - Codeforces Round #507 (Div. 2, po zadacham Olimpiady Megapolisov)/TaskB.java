package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int times = (n + k + k) / (k + k + 1);
        out.println(times);
        int[] answer = new int[times];
        int current = Math.min(n, k + 1);
        for (int i = 0; i < times; ++i) {
            answer[i] = current;
            current += k + k + 1;
        }
        if (answer[times - 1] > n) {
            int diff = answer[times - 1] - n;
            for (int i = 0; i < times; ++i) {
                answer[i] -= diff;
            }
        }
        for (int i = 0; i < times; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i]);
        }
    }
}

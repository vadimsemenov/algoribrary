package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int x = in.nextInt();
        int[] was = new int[100_000 + 1];
        for (int i = 0; i < count; ++i) {
            int a = in.nextInt();
            if (was[a] > 0) {
                out.println(0);
                return;
            }
            was[a] = 1;
        }
        int answer = 3;
        for (int a = 0; a < was.length; ++a) {
            if (was[a] > 0) {
                int b = a & x;
                if (b != a) {
                    if (was[b] == 0) {
                        was[b] = 2;
                    } else if (was[b] == 1) {
                        answer = Math.min(answer, 1);
                    } else if (was[b] == 2) {
                        answer = Math.min(answer, 2);
                    }
                }
            }
        }
        out.println(answer == 3 ? -1 : answer);
    }
}

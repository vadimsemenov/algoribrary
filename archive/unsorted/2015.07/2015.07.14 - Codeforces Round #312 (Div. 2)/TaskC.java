package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        final int MAX = 1 << 20;
        int[] qty = new int[MAX + 1];
        int[] steps = new int[MAX + 1];
        int counter = in.nextInt();
        for (int i = 0; i < counter; ++i) {
            int prev = -1;
            int foo = in.nextInt();
            int step = 0;
            while (prev != 0) {
                steps[foo] += step;
                ++qty[foo];
                if ((foo << 1) != prev && foo != 0) {
                    int _step = step;
                    for (int bar = foo << 1; bar <= MAX; bar <<= 1) {
                        steps[bar] += ++_step;
                        ++qty[bar];
                    }
                }
                prev = foo;
                foo >>>= 1;
                ++step;
            }
        }
        if (qty[0] != counter) throw new AssertionError("WTF?!");
        int answer = steps[0];
        for (int i = 1; i <= MAX; ++i) {
            if (qty[i] == counter) {
                answer = Math.min(answer, steps[i]);
            } else if (qty[i] > counter) {
                throw new AssertionError("WTF?! #2");
            }
        }
        out.println(answer);
    }
}

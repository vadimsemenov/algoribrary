package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        final int ZERO = 1_000_000;
        int[] cnt = new int[ZERO * 2 + 1];
        for (int i = 0; i < counter; ++i) {
            int x = in.nextInt();
            int amount = in.nextInt();
            cnt[x + ZERO] += amount;
        }
        int answer = 0;
        for (int dir = 0; dir < 2; ++dir) {
            int direction = dir;
            int[] ptr = new int[]{ZERO, ZERO};
            int current = 0;
            while (true) {
                final int step = 1 + (-2 * direction);
                while (0 <= ptr[direction] && ptr[direction] < cnt.length && cnt[ptr[direction]] == 0) {
                    ptr[direction] += step;
                }
                if (0 <= ptr[direction] && ptr[direction] < cnt.length) {
                    current += cnt[ptr[direction]];
                    ptr[direction] += step;
                } else {
                    break;
                }
                direction ^= 1;
            }
            answer = Math.max(answer, current);
        }
        out.println(answer);
    }
}

package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int total = in.nextInt();
        int between = in.nextInt();
        int[] minutes = in.nextIntArray(count);
        Integer[] order = new Integer[count];
        Arrays.setAll(order, i -> i);
        Arrays.sort(order, Comparator.comparingInt(i -> minutes[i]));
        int head = 0;
        int days = 0;
        for (int tail = 0; tail < count; ++tail) {
            while (head < tail && minutes[order[tail]] - minutes[order[head]] - 1 >= between) {
                head++;
            }
            days = Math.max(days, tail - head + 1);
        }
        int[] last = new int[days];
        Arrays.fill(last, -between);
        int[] answer = new int[count];
        int ptr = 0;
        for (int i : order) {
            answer[i] = ptr;
            if (minutes[i] - last[ptr] - 1 < between) {
                throw new IllegalStateException("POLUNDRA");
            }
            last[ptr] = minutes[i];
            ptr = (ptr + 1) % days;
        }
        out.println(days);
        for (int i = 0; i < count; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i] + 1);
        }
    }
}

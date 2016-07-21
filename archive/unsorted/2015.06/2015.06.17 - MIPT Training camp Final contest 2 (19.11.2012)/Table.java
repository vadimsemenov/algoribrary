package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Table {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        if (counter % 2 == 0) {
            out.println("IMPOSSIBLE");
        } else {
            int[] cnt = new int[counter];
            cnt[0] = counter;
            int[] succ = new int[counter];
            int[] pred = new int[counter];
            pred[0] = counter - 1;
            succ[0] = 1;
            for (int i = 1; i < counter; ++i) {
                pred[i] = i - 1;
                succ[i] = succ[i - 1] + 1;
            }
            succ[counter - 1] = 0;
            List<Integer> queue = new ArrayList<>();
            int head = 0;
            queue.add(0);
            while (head < queue.size()) {
                int current = queue.get(head++);
                if (cnt[current] < 2) throw new AssertionError();
                cnt[current] -= 2;
                cnt[succ[current]] += 1;
                cnt[pred[current]] += 1;
                if (cnt[succ[current]] == 2) queue.add(succ[current]);
                if (cnt[pred[current]] == 2) queue.add(pred[current]);
                if (cnt[current] > 1) queue.add(current);
            }
            for (int i : queue) {
                out.print((i + 1) + " ");
            }
        }
    }
}

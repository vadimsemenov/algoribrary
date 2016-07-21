package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.PriorityQueue;

public class MaxNumber {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        PriorityQueue<Long> queue = new PriorityQueue<>(counter);
        for (int i = 0; i < counter; ++i) {
            long number = in.nextInt();
            queue.add(number);
        }
        long previous = queue.poll();
        while (queue.size() > 0) {
            long current = queue.poll();
            if (current == previous) {
                queue.add(current << 1);
                previous = queue.poll();
            } else {
                previous = current;
            }
        }
        out.println(previous);
    }
}

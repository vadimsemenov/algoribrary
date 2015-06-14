package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LongQueue {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int threshold = in.nextInt();
        List<Integer> queue = new ArrayList<>(counter);
        List<Integer> answer = new ArrayList<>(counter);
        int head = 0;
        int offset = 0;
        for (int i = 0; i < counter; ++i) {
            queue.add(in.nextInt());
            answer.add(0);
            if (i > 0) {
                int ad = queue.get(i - 1) >= threshold ? 1 : 0;
                answer.set(i, answer.get(i - 1) + ad);
            }
        }
        int queries = in.nextInt();
        for (int query = 0; query < queries; ++query) {
            int type = in.nextInt();
            if (type == 1) {
                int foo = in.nextInt();
                queue.add(foo);
                answer.add(0);
                int last = queue.size() - 1;
                if (last > 0) {
                    int ad = queue.get(last - 1) >= threshold ? 1 : 0;
                    answer.set(last, answer.get(last - 1) + ad);
                }
            } else if (type == 2) {
                if (queue.size() <= head) throw new AssertionError(queue.size());
                if (queue.get(head) >= threshold) {
                    ++offset;
                }
                ++head;
            } else if (type == 3) {
                int idx = in.nextInt();
                if (head + idx >= queue.size()) throw new AssertionError(head + idx);
                out.println(answer.get(head + idx) - offset);
            } else throw new AssertionError(type);
        }
    }
}

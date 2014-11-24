package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int capacity = in.nextInt();
        int[] count = new int[3_000];
        for (int i = 0; i < counter; ++i) {
            count[in.nextInt()]++;
        }
        int current = 0;
        int need = -1;
        long answer = 0;
        for (int i = count.length - 1; i > 1; --i) {
            if (current == capacity) {
                answer += 2 * (need - 1);
                need = -1;
                current = 0;
            }
            if (need == -1 && count[i] > 0) need = i;
            int last = capacity - current;
            if (last >= count[i]) {
                current += count[i];
                count[i] = 0;
            } else {
                count[i] -= last;
                current += last;
                i++;
            }
        }
        if (need != -1) {
            answer += 2 * (need - 1);
        }
        out.println(answer);
    }
}

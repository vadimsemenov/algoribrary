package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int height = in.nextInt();
        int[][] flows = in.nextIntTable(count, 2);
        int head = 0;
        int total = 0;
        int answer = height;
        for (int tail = 0; tail < count; ++tail) {
            int ad = tail == head ? 0 : flows[tail][0] - flows[tail - 1][1];
            total += ad;
            while (head < tail && total >= height) {
                total -= flows[head + 1][0] - flows[head][1];
                head++;
            }
            answer = Math.max(answer, flows[tail][1] - flows[head][0] + height - total);
        }
        out.println(answer);
    }
}

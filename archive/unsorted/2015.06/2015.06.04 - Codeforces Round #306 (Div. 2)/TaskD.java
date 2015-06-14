package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int k = in.nextInt();
        if (k % 2 == 0) {
            out.println("NO");
            return;
        }
        int vertices = 2 + 2 * 2 * (k - 1);
        int edges = vertices * k / 2;
        out.println("YES");
        out.println(vertices + " " + edges);
        int[] v = new int[]{1, 2};
        out.println(v[0] + " " + v[1]); // bridge
        int next = 3;
        for (int it = 0; it < 2; ++it) {
            int from = next;
            for (int i = 1; i < k; ++i) {
                out.println(v[it] + " " + next++);
            }
            int to = next;
            for (int i = 1; i < k; i += 2) {
                out.println((next++) + " " + (next++));
            }
            for (int i = from; i < to; ++i) {
                for (int j = to; j < next; ++j) {
                    out.println(i + " " + j);
                }
            }
        }
    }
}

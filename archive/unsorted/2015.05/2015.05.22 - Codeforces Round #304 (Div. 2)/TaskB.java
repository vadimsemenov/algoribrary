package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] have = new int[2 * counter + 1];
        for (int i = 0; i < counter; ++i) {
            have[in.nextInt()]++;
        }
        int answer = 0;
        for (int i = 0; i + 1 < have.length; ++i) {
            if (have[i] > 1) {
                answer += have[i] - 1;
                have[i + 1] += have[i] - 1;
                have[i] = 1;
            }
        }
        out.println(answer);
    }
}

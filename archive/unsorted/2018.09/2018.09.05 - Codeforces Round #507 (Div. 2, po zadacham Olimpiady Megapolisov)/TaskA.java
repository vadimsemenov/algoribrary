package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] cost = in.nextIntArray(2);
        int[] array = in.nextIntArray(count);
        int answer = 0;
        for (int i = 0; i < count; ++i) {
            if (array[i] == 2) continue;
            int j = count - i - 1;
            if (array[j] == 2) {
                answer += cost[array[i]];
                array[j] = array[i];
            } else if (array[i] != array[j]) {
                out.println("-1");
                return;
            }
        }
        int undefined = 0;
        for (int i = 0; i < count; i++) {
            if (array[i] == 2) {
                undefined++;
            }
        }
        out.println(answer + undefined * Math.min(cost[0], cost[1]));
    }
}

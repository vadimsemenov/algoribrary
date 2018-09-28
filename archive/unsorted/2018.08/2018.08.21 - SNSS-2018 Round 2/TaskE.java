package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int treasury = in.nextInt();
        int[] loss = new int[treasury + 2];
        Arrays.fill(loss, Integer.MAX_VALUE / 3);
        loss[0] = 0;
        for (int i = 0; i < counter; ++i) {
            int capital = in.nextInt();
            int fee = in.nextInt();
            for (int have = treasury; have > -1; --have) {
                int sum = Math.min(have + capital, treasury + 1);
                loss[sum] = Math.min(loss[sum], loss[have] + fee);
            }
        }
        out.println(loss[treasury + 1]);
    }
}

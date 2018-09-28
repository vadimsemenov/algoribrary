package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long minTime = in.nextLong();
        long[] startTime = in.nextLongArray(counter);
        int[] maxPosition = in.nextIntArray(counter);
        int prev = counter;
        for (int i = counter; i --> 0; ) {
            maxPosition[i]--;
            if (prev < maxPosition[i] || maxPosition[i] < i) {
                out.println("No");
                return;
            }
            prev = maxPosition[i];
        }
        long[] finishTime = new long[counter];
        Arrays.fill(finishTime, startTime[counter - 1] + minTime + counter);
        for (int i = 0; i < counter; ++i) {
            if (maxPosition[i] + 1 < counter) {
                finishTime[maxPosition[i]] = startTime[maxPosition[i] + 1] + minTime - 1;
            }
        }
        for (int i = counter - 1; i --> 0; ) {
            finishTime[i] = Math.min(finishTime[i], finishTime[i + 1] - 1);
        }
        for (int i = 0; i < counter; ++i) {
            if (startTime[i] + minTime > finishTime[maxPosition[i]] ||
                    (maxPosition[i] > i && startTime[maxPosition[i]] + minTime > finishTime[maxPosition[i] - 1])) {
                out.println("No");
                return;
            }
        }
        out.println("Yes");
        for (int i = 0; i < counter; ++i) {
            if (i > 0) out.print(' ');
            out.print(finishTime[i]);
        }
    }
}

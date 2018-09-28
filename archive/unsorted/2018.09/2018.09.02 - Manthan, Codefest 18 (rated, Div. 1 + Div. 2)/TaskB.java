package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int median = in.nextInt();
        int[] array = in.nextIntArray(counter);
        ArrayUtils.sort(array);
        long answer = 0;
        for (int i = 0; i < counter; ++i) {
            if (i + i <= counter - 1) {
                answer += Math.max(0, array[i] - median);
            }
            if (i + i >= counter - 1) {
                answer += Math.max(0, median - array[i]);
            }
        }
        out.println(answer);
    }
}

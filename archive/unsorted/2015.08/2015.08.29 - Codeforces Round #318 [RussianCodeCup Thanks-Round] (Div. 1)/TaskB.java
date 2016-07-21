package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[2 + counter];
        for (int i = 1; i <= counter; ++i) {
            array[i] = in.nextInt();
        }
        int[] prefix = new int[2 + counter];
        Arrays.fill(prefix, Integer.MAX_VALUE);
        int[] suffix = new int[2 + counter];
        Arrays.fill(suffix, Integer.MAX_VALUE);
        prefix[0] = 0;
        suffix[counter + 1] = 0;
        for (int i = 1; i < array.length; ++i) {
            prefix[i] = Math.min(array[i], 1 + prefix[i - 1]);
        }
        for (int i = array.length - 2; i >= 0; --i) {
            suffix[i] = Math.min(array[i], 1 + suffix[i + 1]);
        }
        int answer = 0;
        for (int i = 0; i < array.length; ++i) {
            answer = Math.max(answer, Math.min(prefix[i], suffix[i]));
        }
        out.println(answer);
    }
}

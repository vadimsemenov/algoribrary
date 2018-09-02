package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] elements = in.nextIntArray(counter);
        Arrays.sort(elements);
        out.println(elements[(counter  - 1) / 2]);
    }
}

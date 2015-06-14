package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int took = in.nextInt();
        int[] a = new int[counter];
        int[] b = new int[counter];
        for (int i = 0; i < counter; ++i) a[i] = in.nextInt();
        for (int i = 0; i < counter; ++i) b[i] = in.nextInt();
        Arrays.sort(a);
        Arrays.sort(b);
        int diff = 0;
        for (int i = 0; i < took; ++i) {
            diff += a[i] - b[counter - 1 - i];
        }
        out.println(diff > 0 ? "YES" : "NO");
    }
}

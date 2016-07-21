package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    private int[] xs, ys;
    private Integer[] order;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        xs = new int[counter];
        ys = new int[counter];
        order = new Integer[counter];
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < counter; i++) {
            xs[i] = in.nextInt();
            ys[i] = in.nextInt();
            max = Math.max(max, xs[i]);
            min = Math.min(min, xs[i]);
            order[i] = i;
        }
        out.println(answer);
    }
}

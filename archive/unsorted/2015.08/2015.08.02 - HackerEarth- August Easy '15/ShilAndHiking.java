package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAndHiking {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        long[] xs = new long[counter];
        long[] ys = new long[counter];
        for (int i = 0; i < counter; ++i) {
            xs[i] = in.nextInt();
            ys[i] = in.nextInt();
        }
        int answer = 0;
        for (int i = 0; i < counter; ++i) {
            long x = 0;
            long y = 0;
            for (int j = i + 1; j < counter; ++j) {
                long xx = xs[j] - xs[i];
                long yy = ys[j] - ys[i];
                if (yy * x >= y * xx) {
                    System.err.println(i + " " + j);
                    ++answer;
                    x = xx;
                    y = yy;
                }
            }
        }
        out.println(answer);
    }
}

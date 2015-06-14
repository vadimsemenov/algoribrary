package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int modulo = in.nextInt();
        int[] h = new int[2];
        int[] a = new int[2];
        int[] x = new int[2];
        int[] y = new int[2];
        for (int it = 0; it < 2; ++it) {
            h[it] = in.nextInt();
            a[it] = in.nextInt();
            x[it] = in.nextInt();
            y[it] = in.nextInt();
        }
        int[] p = new int[2];
        int[] c = new int[2];
        for (int it = 0; it < 2; ++it) {
            int[] predperiod = new int[modulo];
            Arrays.fill(predperiod, -1);
            int current = h[it];
            while (predperiod[current] == -1) {
                predperiod[current] = c[it]++;
                current = (int) ((x[it] * 1L * current + y[it]) % modulo);
            }
            c[it] -= predperiod[current];
            p[it] = predperiod[a[it]];
            boolean after = false;
            for (int i = 0; i < c[it]; ++i) {
                after |= current == a[it];
                current = (int) ((x[it] * 1L * current + y[it]) % modulo);
            }
            if (!after) {
                out.println(-1);
                return;
            }
        }
    }
}

package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        double[] max = in.nextDoubleArray(counter);
        double[] min = in.nextDoubleArray(counter);
        double[] smin = new double[counter + 1];
        double[] smax = new double[counter + 1];
        for (int i = 1; i <= counter; ++i) {
            smin[i] = smin[i - 1] + min[i - 1];
            smax[i] = smax[i - 1] + max[i - 1];
        }
        double[] p = new double[counter + 1];
        p[0] = 0;
        for (int i = 1; i <= counter; ++i) {
            double B = (smin[i] + smax[i]);
            double C = smax[i];
            double D = B * B - 4 * C;
            p[i] = (B + Math.sqrt(D)) / 2.0;
        }
        for (int i = counter - 1; i > 0; --i) {
            p[i + 1] -= p[i];
            if (p[i + 1] < 1e-9) p[i + 1] = 0;
        }
        for (int i = 1; i <= counter; ++i) {
            out.print(p[i] + " ");
        }
        out.println();
        for (int i = 1; i <= counter; ++i) {
            out.print((min[i - 1] + max[i - 1] - p[i]) + " ");
        }
    }
}

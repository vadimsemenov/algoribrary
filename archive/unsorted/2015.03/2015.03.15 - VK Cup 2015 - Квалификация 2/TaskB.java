package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int time = in.nextInt();
        double c = in.nextDouble();
        double[] approx = new double[counter + 1];
        long[] sums = new long[counter + 1];
        for (int i = 1; i <= counter; ++i) {
            int foo = in.nextInt();
            approx[i] = (approx[i - 1] + foo * 1.0 / time) / c;
            sums[i] = foo + sums[i - 1];
        }
        int queries = in.nextInt();
        for (int i = 0; i < queries; ++i) {
            int bar = in.nextInt();
            double real = sums[bar] - sums[bar - time];
            real /= time;
            out.printf("%5f %5f %5f\n", real, approx[bar], Math.abs(real - approx[bar]) / real);
        }
    }
}

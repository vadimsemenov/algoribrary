package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskH {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int[] values = new int[4];
        for (int i = 0; i < 4; ++i) values[i] = in.nextInt();
        double right = 1e9;
        double left = 0;
        for (int it = 0; it < 333; ++it) {
            double norm = (left + right) / 2;
            boolean check = true;
            for (int i = 0; i < 2; ++i) {
                int j = 3 - i;
                for (int ii = -1; ii < 2; ii += 2) {
                    for (int jj = -1; jj < 2; jj += 2) {
                        double vi = values[i] + ii * norm;
                        double vj = values[j] + jj * norm;
                        double li = Math.min(values[i ^ 1] + norm, values[i ^ 1] - norm);
                        double ri = Math.max(values[i ^ 1] + norm, values[i ^ 1] - norm);
                        double lj = Math.min(values[j ^ 1] + norm, values[j ^ 1] - norm);
                        double rj = Math.max(values[j ^ 1] + norm, values[j ^ 1] - norm);

                    }
                }
            }
            if (check) {
                right = norm;
            } else {
                left = norm;
            }
        }
        out.println(right);
    }
}

package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] params = in.nextIntArray(count);
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int i = in.nextInt() - 1;
            int vi = in.nextInt();
            int j = in.nextInt() - 1;
            int vj = in.nextInt();
            if (i > j) {
                int t = i;
                i = j;
                j = t;
                t = vi;
                vi = vj;
                vj = t;
            }
            int dvi = vi - params[i];
            int dvj = vj - params[j];
            if (dvi == 0 && dvj == 0) {
                out.println("=");
            } else if (dvi >= 0 && dvj >= 0) {
                out.println(">");
            } else if (dvi <= 0 && dvj <= 0) {
                out.println("<");
            } else {
                int power = j - i;
                if (i == 0) power--;
                long advi = Math.abs(dvi);
                long advj = Math.abs(dvj);
                if (advi >>> power == advj && advj << power == advi) {
                    out.println("=");
                } else if (advi >>> power > advj) {
                    out.println(dvi < 0 ? "<" : ">");
                } else {
                    out.println(dvj < 0 ? "<" : ">");
                }
            }
            params[i] = vi;
            params[j] = vj;
        }
    }
}

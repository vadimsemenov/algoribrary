package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Divisiors {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        while (a != 0 && b != 0) {
            long result = 0;
            for (long d = 1; d * d <= b; d++) {
                if (a <= d * d) result++;
                result += 2 * (b / d - Math.max(a - 1, d * d) / d);
            }
            out.println(result);

            a = in.nextInt();
            b = in.nextInt();
        }
    }
}

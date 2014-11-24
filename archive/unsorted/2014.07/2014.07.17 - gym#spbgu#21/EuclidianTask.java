package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class EuclidianTask {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        while (in.hasNext()) {
            long a = in.nextInt();
            long b = in.nextInt();
            long x = 1;
            long y = 0;
            long xx = 0;
            long yy = 1;
            while (b != 0) {
                long q = a / b;
                long tmp = a - b * q;
                a = b;
                b = tmp;
                tmp = x - q * xx;
                x = xx;
                xx = tmp;
                tmp = y - q * yy;
                y = yy;
                yy = tmp;
            }
            out.println(x + " " + y + " " + a);
        }
    }
}

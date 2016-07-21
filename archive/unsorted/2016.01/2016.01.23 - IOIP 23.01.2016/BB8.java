package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class BB8 {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int aa = in.nextInt();
        int bb = in.nextInt();
        out.println(((aa + a - 1) / a) * ((bb + b - 1) / b));
    }
}

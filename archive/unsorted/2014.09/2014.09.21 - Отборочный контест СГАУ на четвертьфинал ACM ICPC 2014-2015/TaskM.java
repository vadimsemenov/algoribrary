package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskM {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int inc = in.nextInt();
        int dec = in.nextInt();
        out.println(inc * dec);
        for (int i = 0; i < inc; ++i) {
            for (int j = 0; j < dec; ++j) {
                out.print((i + 1) * dec - j);
                out.print(' ');
            }
        }
    }
}

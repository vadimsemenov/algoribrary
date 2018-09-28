package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int min = in.nextInt();
        int max = in.nextInt();
        int len = 0;
        while (len * 9 < min) {
            out.print(9);
            len++;
        }
        out.println();
        for (int i = 0; i < len; ++i) {
            out.print(9);
        }
        for (int i = 1; i < len; ++i) {
            out.print(0);
        }
        out.println(1);
    }
}

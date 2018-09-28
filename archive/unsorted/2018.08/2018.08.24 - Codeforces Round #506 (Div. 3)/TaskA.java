package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int count = in.nextInt();
        String pattern = in.next();
        int i = length - 1;
        while (i > 0 && !pattern.endsWith(pattern.substring(0, i))) {
            i--;
        }
        out.print(pattern);
        for (int k = 1; k < count; ++k) {
            out.print(pattern.substring(i));
        }
        out.println();
    }
}

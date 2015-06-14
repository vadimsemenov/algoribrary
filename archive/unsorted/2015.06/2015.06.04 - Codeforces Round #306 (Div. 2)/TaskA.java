package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        String input = in.next();
        int ab1 = input.indexOf("AB");
        int ab2 = input.lastIndexOf("AB");
        int ba1 = input.indexOf("BA");
        int ba2 = input.lastIndexOf("BA");
        if (ab1 == -1 || ba1 == -1) {
            out.println("NO");
        } else if (Math.abs(ab1 - ba2) > 1 || Math.abs(ab2 - ba1) > 1) {
            out.println("YES");
        } else {
            out.println("NO");
        }
    }
}

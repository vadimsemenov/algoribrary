package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        if (n == 0) {
            out.println("0 1");
            out.println("0 " + m);
            out.println("0 0");
            out.println("0 " + (m - 1));
        } else if (m == 0) {
            out.println("1 0");
            out.println(n + " 0");
            out.println("0 0");
            out.println((n - 1) + " 0");
        } else {
            
            out.println(n + " " + m);
            out.println("0 0");
        }
    }
}

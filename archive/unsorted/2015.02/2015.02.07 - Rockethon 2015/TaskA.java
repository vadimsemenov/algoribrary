package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n1 = in.nextInt();
        int n2 = in.nextInt();
        out.println(n1 > n2 ? "First" : "Second");
    }
}

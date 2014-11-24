package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] input = in.next().toCharArray();
        for (int i = input.length - 1; i >= 0; --i) {
            out.print(input[i]);
        }
        out.println();
    }
}

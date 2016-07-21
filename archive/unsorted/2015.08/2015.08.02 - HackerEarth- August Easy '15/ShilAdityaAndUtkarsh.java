package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAdityaAndUtkarsh {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        long answer = 0;
        for (int j = 3; j <= counter; ++j) {
            answer += (j - 1) * (j - 2);
        }
        out.println(answer);
    }
}

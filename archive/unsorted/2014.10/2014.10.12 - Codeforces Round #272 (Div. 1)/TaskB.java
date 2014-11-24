package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int gcd = in.nextInt();
        out.println(gcd * (6 * (counter - 1) + 5));
        int[] plus = {1, 2, 3, 5};
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < 4; ++j) {
                out.print(gcd * (6 * i + plus[j]));
                out.print(' ');
            }
            out.println();
        }
    }
}

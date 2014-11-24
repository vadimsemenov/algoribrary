package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long a = in.nextInt();
        long b = in.nextInt();
        final long MODULO = 1_000_000_007;
        long answer = b * (b - 1) / 2 % MODULO;
        long tmp = a * (a + 1) / 2 % MODULO;
        answer *= (tmp * b + a) % MODULO;
        out.println(answer % MODULO);
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] power = in.nextIntArray(counter);
        long sum = 0;
        for (int p : power) sum += p;
        boolean foundLast = false;
        for (int p : power) {
            if (p * 2 == sum && !foundLast) {
                foundLast = true;
            } else {
                out.print(p + " ");
            }
        }
        out.println(sum / 2);
    }
}

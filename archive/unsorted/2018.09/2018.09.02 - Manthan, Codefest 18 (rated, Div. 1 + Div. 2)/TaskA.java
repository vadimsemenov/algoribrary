package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int need = in.nextInt();
        int counter = 0;
        while ((1 << counter) < need) {
            need -= (1 << counter);
            ++counter;
        }
        if (need > 0) {
            counter++;
        }
        out.println(counter);
    }
}

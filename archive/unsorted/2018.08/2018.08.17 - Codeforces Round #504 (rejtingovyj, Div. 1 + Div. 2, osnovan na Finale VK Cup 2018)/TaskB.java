package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        long counter = in.nextLong();
        long totalCost = in.nextLong();
        counter = Math.min(counter, totalCost - 1);
        long min = totalCost - counter;
        if (min >= counter) {
            out.println(0);
        } else {
            out.println((counter - min + 1) / 2);
        }
    }
}

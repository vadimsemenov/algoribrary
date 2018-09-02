package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int hours = in.nextInt();
        int minutes = in.nextInt();
        out.println((12 - hours) % 12 + " " + (60 - minutes) % 60);
    }
}

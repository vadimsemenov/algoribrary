package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int radius = in.nextInt();
        int x1 = in.nextInt();
        int y1 = in.nextInt();
        int x2 = in.nextInt();
        int y2 = in.nextInt();
        long dx = x1 - x2;
        long dy = y1 - y2;
        long hypot = dx * dx + dy * dy;
        double dist = Math.sqrt(hypot);
        out.println((int) Math.ceil(dist / (2 * radius)));
    }
}

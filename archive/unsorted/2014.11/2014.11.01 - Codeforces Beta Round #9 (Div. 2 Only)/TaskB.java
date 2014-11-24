package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int busSpeed = in.nextInt();
        int studentSpeed = in.nextInt();
        int[] xs = new int[counter];
        for (int i = 0; i < counter; ++i) {
            xs[i] = in.nextInt();
        }
        int x = in.nextInt();
        int y = in.nextInt();
        int best = -1;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (int i = 1; i < counter; ++i) {
            double currentDistance = xs[i] * 1.0 / busSpeed + Math.sqrt(y * 1.0 * y + (x - xs[i]) * 1.0 * (x - xs[i])) / studentSpeed;
            if (currentDistance < bestDistance + 1e-9) {
                bestDistance = currentDistance;
                best = i + 1;
            }
        }
        out.println(best);
    }
}

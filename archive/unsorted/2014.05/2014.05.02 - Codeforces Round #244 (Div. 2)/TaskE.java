package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int block = in.nextInt();
        long[] positions = new long[counter];
        for (int i = 0; i < counter; i++) positions[i] = in.nextInt();
        long left = 0;
        long right = 0;
        for (int i = 0; i < counter; i++) if (i % block == 0) right += positions[counter - i - 1] - positions[0];
        long minDistance = left + right;
        for (int i = 1; i < counter; i++) {
            left += ((i + block - 1) / block) * (positions[i] - positions[i - 1]);
            right -= ((counter - i + block - 1) / block) * (positions[i] - positions[i - 1]);
            minDistance = Math.min(minDistance, left + right);
        }
        out.println(2 * minDistance);
    }
}

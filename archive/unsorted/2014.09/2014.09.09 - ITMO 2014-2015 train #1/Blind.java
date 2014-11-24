package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Blind {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] max = new int[2];
        for (int i = 0; i < counter; ++i) {
            int value = in.nextInt();
            int type = in.nextInt();
            max[type] = Math.max(max[type], value);
        }
        out.println(max[0] > max[1] ? (max[0] + (max[0] - max[1])) : max[1]);
    }
}

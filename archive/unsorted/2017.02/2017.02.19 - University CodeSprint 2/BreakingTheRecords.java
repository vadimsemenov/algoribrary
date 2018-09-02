package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class BreakingTheRecords {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int min, max;
        min = max = in.nextInt();
        int minBreaks = 0;
        int maxBreaks = 0;
        for (int i = 1; i < counter; ++i) {
            int x = in.nextInt();
            if (x < min) {
                ++minBreaks;
                min = x;
            }
            if (x > max) {
                ++maxBreaks;
                max = x;
            }
        }
        out.println(maxBreaks + " " + minBreaks);
    }
}

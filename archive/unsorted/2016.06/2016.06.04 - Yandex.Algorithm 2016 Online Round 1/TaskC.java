package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    private final int total = 1000;
    private int[] ad = {100, 75, 60, 50, 45, 40, 36, 32, 29, 26, 24, 22, 20, 18, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] diff;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        diff = new int[ad.length];
        for (int i = 0; i < 10; ++i) diff[i] = in.nextInt();
        for (int i = 10; i < diff.length; ++i) diff[i] = diff[9];
        for (int place = 30; place >= 0; --place) {
            if (can(place)) {
                out.println(place == 30 ? total : place + 1);
                return;
            }
        }
        out.println(0);
    }

    private boolean can(int place) {
        int threshold = ad[place];
        int ptr = ad.length - 1;
        int i;
        for (i = 0; i < 10; ++i) {
            while (ptr >= 0 && (diff[i] + ad[ptr] < threshold || ptr == place)) {
                --ptr;
            }
            if (ptr < 0) return true;
            --ptr;
        }
        return false;
    }
}

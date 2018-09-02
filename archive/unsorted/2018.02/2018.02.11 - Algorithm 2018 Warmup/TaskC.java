package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] xs = new int[counter];
        int[] ys = new int[counter];
        for (int i = 0; i < counter; ++i) {
            int type = in.nextInt();
            if (type == 0) {
                int radius = in.nextInt();
                xs[i] = in.nextInt() * 4;
                ys[i] = in.nextInt() * 4;
            } else if (type == 1) {
                int x = 0;
                int y = 0;
                for (int j = 0; j < 4; ++j) {
                    x += in.nextInt();
                    y += in.nextInt();
                }
                xs[i] = x;
                ys[i] = y;
            } else throw new RuntimeException("WTF: " + type);
        }
        int base = 1;
        while (base < counter && xs[base] == xs[0] && ys[base] == ys[0]) {
            ++base;
        }
        if (base < counter) {
            for (int i = base + 1; i < counter; ++i) {
                if ((long) (xs[i] - xs[base]) * (ys[0] - ys[base]) != (long) (ys[i] - ys[base]) * (xs[0] - xs[base])) {
                    out.println("No");
                    return;
                }
            }
        }
        out.println("Yes");
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        boolean[][] table = new boolean[n + 2][m + 2];
        for (int i = 0; i < k; ++i) {
            int x = in.nextInt();
            int y = in.nextInt();
            table[x][y] = true;
            for (int xx = x - 1; xx <= x; ++xx) {
                for (int yy = y - 1; yy <= y; ++yy) {
                    boolean allBlack = true;
                    for (int xxx = xx; xxx < xx + 2; ++xxx) {
                        for (int yyy = yy; yyy < yy + 2; ++yyy) {
                            allBlack &= table[xxx][yyy];
                        }
                    }
                    if (allBlack) {
                        out.println(i + 1);
                        return;
                    }
                }
            }
        }
        out.println(0);
    }
}

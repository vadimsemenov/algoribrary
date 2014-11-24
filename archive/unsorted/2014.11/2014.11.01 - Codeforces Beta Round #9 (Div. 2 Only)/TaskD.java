package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int h = in.nextInt();
        mem = new long[n + 1][n + 1];
        anotherMem = new long[n + 1][n + 1];
        for (long[] m : mem) Arrays.fill(m, -1);
        for (long[] m : anotherMem) Arrays.fill(m, -1);
        out.println(rec(n, h));
    }

    private long[][] mem;
    private long[][] anotherMem;
    private long rec(int n, int h) {
        if (n < h) {
            return 0;
        }
        if (mem[n][h] == -1) {
            mem[n][h] = 0;
            if (n == (1L << h) - 1) {
                mem[n][h] = 1;
            } else if (n < (1L << h) - 1) {
                for (int inLeft = 0; inLeft <= n - 1; ++inLeft) {
                    for (int leftHeight = 0; leftHeight <= h - 1; ++leftHeight) {
                        rec(inLeft, leftHeight);
                    }
                }
                for (int inLeft = 0; inLeft <= n - 1; ++inLeft) {
                    for (int leftHeight = 0; leftHeight < h - 1; ++leftHeight) {
                        mem[n][h] += 2 * Math.max(0, mem[inLeft][leftHeight]) * Math.max(0, mem[n - 1 - inLeft][h - 1]);
                    }
                    mem[n][h] += Math.max(0, mem[inLeft][h - 1]) * Math.max(0, mem[n - 1 - inLeft][h - 1]);
                }
            }
            anotherMem[n][h] = mem[n][h] + rec(n, h + 1);
        }
        return anotherMem[n][h];
    }
}

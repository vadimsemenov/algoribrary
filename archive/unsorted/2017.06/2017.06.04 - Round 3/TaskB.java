package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        String nn = in.next();
        int[] n = new int[nn.length()];
        for (int i = 0; i < n.length; ++i) n[i] = nn.charAt(i) - '0';
        int x = in.nextInt();
        int y = in.nextInt();
        boolean less = false;
        int lastY = -1;
        int start = 0;
        for (int i = 0; i < n.length; ++i) {
            if (less) {
                n[i] = y;
            } else {
                if (n[i] > y) {
                    n[i] = y;
                    less = true;
                } else if (n[i] == y) {
                    lastY = i;
                    continue;
                } else if (n[i] > x) {
                    n[i] = x;
                    less = true;
                } else if (n[i] == x) {
                    continue;
                } else if (n[i] < x) {
                    if (lastY != -1) {
                        n[lastY] = x;
                        i = lastY;
                    } else {
                        start = 1;
                        i = 0;
                    }
                    less = true;
                }
            }
        }
        while (start < n.length && n[start] == 0) ++start;
        if (start == n.length) {
            out.println(-1);
        } else for (int i = start; i < n.length; ++i) {
            out.print(n[i]);
        }
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        char[] s = in.next().toCharArray();
        char[] t = in.next().toCharArray();
        int[] cnt = new int[256];
        for (int c : s) ++cnt[c];
        for (int c : t) --cnt[c];
        for (int i : cnt) if (i != 0) {
            out.println(-1);
            return;
        }

    }
}

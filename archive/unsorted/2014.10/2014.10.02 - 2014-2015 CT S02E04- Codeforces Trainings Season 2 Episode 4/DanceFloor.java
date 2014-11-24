package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class DanceFloor {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int maxm = 0;
        int mw = 0;
        int maxw = 0;
        int wm = 0;
        for (int i = 0; i < counter; ++i) {
            int M = in.nextInt();
            int W = in.nextInt();
            int S = in.nextInt();
            int m = Math.min(M, S);
            int w = Math.min(W, Math.max(0, S - m));
            maxm += m;
            mw += w;
            w = Math.min(W, S);
            m = Math.min(M, Math.max(0, S - w));
            maxw += w;
            wm += m;
        }
        if (maxm <= mw) {
            out.println(maxm);
        } else if (maxw <= wm) {
            out.println(maxw);
        } else {
            out.println(Math.min((maxm + mw) / 2, (maxw + wm) / 2));
        }
    }
}

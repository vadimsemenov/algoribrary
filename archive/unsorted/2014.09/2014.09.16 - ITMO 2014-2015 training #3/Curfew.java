package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Curfew {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int houses = in.nextInt();
        int children = in.nextInt();
        int cur = children;
        int answer = 0;
        for (int i = 0; i < houses; ++i) {
            int inCurrent = in.nextInt();
            if (inCurrent > children) {
                out.println(-1);
                return;
            }
            if (inCurrent > cur) {
                answer++;
                cur = children;
            }
            cur -= inCurrent;
        }
        if (answer == 0 && cur != 0) {
            if (houses == 1) {
                out.println(-1);
                return;
            }
            answer++;
        }
        out.println(answer);
    }
}

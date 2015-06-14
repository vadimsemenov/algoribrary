package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TTENIS {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] match = in.next().toCharArray();
        int fst = 0;
        int snd = 0;
        for (int i = 0; i < match.length; ++i) {
            if (match[i] == '1') {
                ++fst;
            } else {
                ++snd;
            }
            if (fst >= 10 && snd >= 10) {
                if (Math.abs(fst - snd) >= 2) {
                    out.println(fst > snd ? "WIN" : "LOSE");
                    return;
                }
            } else {
                if (fst > 10) {
                    out.println("WIN");
                    return;
                } else if (snd > 10) {
                    out.println("LOSE");
                    return;
                }
            }
        }
        throw new AssertionError("WTF?!");
    }
}

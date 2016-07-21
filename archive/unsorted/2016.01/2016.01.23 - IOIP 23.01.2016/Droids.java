package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Droids {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int length = in.nextInt();
        int counter = in.nextInt();
        char[] hole = ("#" + in.next() + "#").toCharArray();
        char[] algo = in.next().toCharArray();
        if (hole.length != length + 2 || algo.length != counter) {
            throw new AssertionError("impossible");
        }
        int min = 0;
        int max = 0;
        int current = 0;
        for (char ch : algo) {
            if (ch == 'L') {
                --current;
            } else if (ch == 'R') {
                ++current;
            } else {
                throw new AssertionError("bad character");
            }
            min = Math.min(min, current);
            max = Math.max(max, current);
        }
        int previous = -1;
        int answer = 0;
        boolean[] bad = new boolean[hole.length];
        for (int i = 0; i < hole.length; ++i) {
            if (hole[i] == '#') {
                int next = i;
                for (int j = previous + 1; j < next; ++j) {
                    if (hole[j] == 'D' && (j + min <= previous || j + max >= next)) {
                        --answer;
                        bad[j] = true;
                    }
                }
                previous = next;
            } else if (hole[i] == 'D') {
                ++answer;
            } else if (hole[i] != '.') {
                throw new AssertionError("WTF?! '" + hole[i] + "'");
            }
        }
        out.println(answer);
        for (int i = 0; i < hole.length; ++i) {
            if (hole[i] == 'D') {
                if (!bad[i]) {
                    out.print(i + " ");
                }
            }
        }
        out.println();
    }
}

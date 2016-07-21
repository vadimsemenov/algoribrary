package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskF {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        testing: while (tests --> 0) {
            int interesting = in.nextInt();
            int prev = in.nextInt();
            int[] prefix = new int[interesting - 1];
            for (int i = 0; i + 1 < interesting; ++i) {
                int cur = in.nextInt();
                prefix[i] = cur - prev;
                prev = cur;
            }
            int length = in.nextInt();
            int[] text = new int[Math.max(interesting - 1 + length - 1 + 1, 0)];
            if (length > 0) {
                System.arraycopy(prefix, 0, text, 0, prefix.length);
                text[prefix.length] = Integer.MIN_VALUE;
                prev = in.nextInt();
                for (int i = prefix.length + 1; i < text.length; ++i) {
                    int cur = in.nextInt();
                    text[i] = cur - prev;
                    prev = cur;
                }
            }
            if (interesting > length) {
                out.println(0);
                continue testing;
            }
            if (interesting == 1) {
                out.println(1);
                continue testing;
            }
            int[] pf = new int[text.length];
            for (int i = 1, k = 0; i < text.length; ++i) {
                while (k > 0 && text[k] != text[i]) {
                    k = pf[k - 1];
                }
                if (text[k] == text[i]) ++k;
                pf[i] = k;
                if (k == prefix.length) {
                    out.println(1);
                    continue testing;
                }
            }
            out.println(0);
        }
    }
}

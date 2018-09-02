package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public final class SeparateTheNumbers {
    public void solve(int __, InputReader in, PrintWriter out) {
        String s = in.next();
        if (s.charAt(0) == '0') {
            out.println("NO");
            return;
        }
        outer:
        for (int len = 1; len + len <= s.length(); ++len) {
            long first = Long.parseLong(s.substring(0, len));
            long next = first + 1;
            int pos = len;
            while (pos != s.length()) {
                String nextString = "" + next;
                if (s.length() < pos + nextString.length() || !nextString.equals(s.substring(pos, pos + nextString.length()))) {
                    continue outer;
                }
                pos += nextString.length();
                ++next;
            }
            out.println("YES " + first);
            return;
        }
        out.println("NO");
    }
}

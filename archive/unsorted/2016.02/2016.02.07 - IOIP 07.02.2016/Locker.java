package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Locker {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] s = in.next().toCharArray();
        int middleL = (s.length - 1) / 2;
        int middleR = middleL + (s.length % 2 == 1 ? 0 : 1);
        int i = 0;
        while (i + i < s.length && s[i] == s[s.length - 1 - i]) {
            ++i;
        }
        int j = 0;
        while (j + j < s.length && s[middleL - j] == s[middleR + j]) {
            ++j;
        }
        if (check(s) || check(swap(s, i, middleL - j)) || check(swap(s, i, middleR + j)) ||
                check(swap(s, s.length - 1 - i, middleL - j)) || check(swap(s, s.length - 1 - i, middleR + j)) ||
                (middleL == middleR && i == middleL - j &&
                        (check(swap(s, middleL, middleL - j)) || check(swap(s, middleL, middleR + j))))) {
            out.println("YES");
        } else {
            out.println("NO");
        }
    }

    private char[] swap(char[] s, int i, int j) {
        s = s.clone();
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
        return s;
    }

    private boolean check(char[] s) {
        for (int i = 0; i + i < s.length; ++i) {
            if (s[i] != s[s.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }
}

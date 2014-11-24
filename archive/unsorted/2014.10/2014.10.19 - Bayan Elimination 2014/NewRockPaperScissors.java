package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class NewRockPaperScissors {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int r = 0, s = 0, p = 0;
        String input = in.next();
        for (char ch : input.toCharArray()) {
            if (ch == 'R') {
                r++;
            } else if (ch == 'S') {
                s++;
            } else if (ch == 'P') {
                p++;
            } else {
                throw new AssertionError(ch);
            }
        }
        int max = Math.max(p == 0 ? 0 : r, Math.max(s == 0 ? 0 : p, r == 0 ? 0 : s));
        int answer = 0;
        if (r == max) {
            answer += p;
        }
        if (s == max) {
            answer += r;
        }
        if (p == max) {
            answer += s;
        }
        out.println("Case #" + testNumber + ":");
        out.println(answer);
    }
}

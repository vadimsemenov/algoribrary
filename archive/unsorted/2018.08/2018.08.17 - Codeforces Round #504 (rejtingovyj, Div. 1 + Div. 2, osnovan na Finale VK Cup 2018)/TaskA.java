package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        String pattern = in.next();
        String text = in.next();
        int wildcard = pattern.indexOf('*');
        if (wildcard < 0) {
            out.println(text.equals(pattern) ? "YES" : "NO");
        } else if (n - 1 > m) {
            out.println("NO");
        } else {
            out.println(text.startsWith(pattern.substring(0, wildcard)) && text.endsWith(pattern.substring(wildcard + 1)) ? "YES" : "NO");
        }
    }
}

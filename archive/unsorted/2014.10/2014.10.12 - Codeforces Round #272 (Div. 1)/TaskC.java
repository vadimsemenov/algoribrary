package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String initial = in.next();
        String pattern = in.next();
        String s = pattern + "#" + initial;
        int[] prefixFunction = new int[s.length()];
        for (int i = 1; i < s.length(); ++i) {
            int k = prefixFunction[i - 1];
            while (k > 0 && s.charAt(k) != s.charAt(i)) {
                k = prefixFunction[k - 1];
            }
            if (s.charAt(k) == s.charAt(i)) {
                k++;
            }
            prefixFunction[i] = k;
        }
        int[] canRemove = new int[initial.length()];
        for (int i = pattern.length() + 1; i < s.length(); ++i) {
            //if (prefixFunction[i] ==     )
        }
    }
}

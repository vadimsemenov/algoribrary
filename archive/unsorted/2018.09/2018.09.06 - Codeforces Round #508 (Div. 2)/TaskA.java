package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int k = in.nextInt();
        String string = in.next();
        int[] qty = new int[k];
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) < 'A' + k) {
                qty[string.charAt(i) - 'A']++;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i : qty) min = Math.min(min, i);
        out.println(min * k);
    }
}

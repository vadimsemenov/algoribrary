package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String max = in.next();
        int answer = 0;
        int i;
        for (i = 0; i < max.length(); ++i) {
            if (max.charAt(i) > '1') {
                answer += 1 << (max.length() - i);
                break;
            }
            if (max.charAt(i) == '1') {
                answer += 1 << (max.length() - i - 1);
            }
        }
        out.println(answer - (i == max.length() ? 0 : 1));
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int maximal = in.nextInt();
        int length = in.nextInt();
        if (length == 0) {
            out.println(1);
            return;
        }
        int previous = 0;
        long answer = 0;
        for (int i = 1; i <= counter; i++) {
            int current = in.nextInt();
            if (current > maximal) {
                if (i - 1 > previous && (i - 1 - previous) >= length)
                    answer += (i - 1 - previous - length + 1);
                previous = i;
            }
        }
        if (counter - previous + 1 >= length) answer += (counter - previous - length + 1);
        out.println(answer);
    }
}

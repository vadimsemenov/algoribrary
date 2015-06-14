package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int tests = in.nextInt();
        for (int test = 0; test < tests; ++test) {
            int a = in.nextInt();
            int b = in.nextInt();
            int k = in.nextInt();
            if (a < b) {
                int tmp = a; a = b; b = tmp;
            }
            if (a > 99 && b > 99 && k == 1) {
                out.println(0);
                continue;
            }
            int answer = -1;
            for (int day = Math.min(99, b); day > 0; --day) {
                int another = day * k;
                if (another >= 100) continue;
                int ans = b - day;
                if (Math.min(99, a - ans) == another) {
                    answer = ans;
                    break;
                }
            }
            out.println(answer);
        }
    }
}

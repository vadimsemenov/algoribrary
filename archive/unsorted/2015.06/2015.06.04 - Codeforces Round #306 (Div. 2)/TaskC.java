package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] number = in.next().toCharArray();
        for (int i = 0; i < number.length; ++i) {
            int num = number[i] - '0';
            if (num % 8 == 0) {
                out.println("YES");
                out.println(num);
                return;
            }
            for (int j = i + 1; j < number.length; ++j) {
                num = num * 10 + number[j] - '0';
                if (num % 8 == 0) {
                    out.println("YES");
                    out.println(num);
                    return;
                }
                for (int k = j + 1; k < number.length; ++k) {
                    num = num * 10 + number[k] - '0';
                    if (num % 8 == 0) {
                        out.println("YES");
                        out.println(num);
                        return;
                    }
                    num /= 10;
                }
                num /= 10;
            }
        }
        out.println("NO");
    }
}

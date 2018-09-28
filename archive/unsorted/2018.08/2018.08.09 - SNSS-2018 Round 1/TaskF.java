package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int groups = in.nextInt();
        int answer = -1;
        for (int i = 0; i < groups; ++i) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int ab = in.nextInt();
            int bc = in.nextInt();
            int ac = in.nextInt();
            int abc = in.nextInt();
            int count = abc;
            ab -= abc;
            ac -= abc;
            bc -= abc;
            a -= abc;
            b -= abc;
            c -= abc;
            count += ab + ac + bc;
            a -= ab + ac;
            b -= ab + bc;
            c -= ac + bc;
            count += a + b + c;
            if (a < 0 || b < 0 || c < 0 || ab < 0 || bc < 0 || ac < 0) {
                continue;
            }
            answer = Math.max(answer, count);
        }
        out.println(answer);
    }
}

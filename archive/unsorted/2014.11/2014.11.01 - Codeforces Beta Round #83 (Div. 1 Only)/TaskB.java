package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int need = in.nextInt() - 1;
        int departments = in.nextInt();
        int my = in.nextInt();
        int inMy = -1;
        int inOther = 0;
        for (int i = 0; i < departments; ++i) {
            int counter = in.nextInt();
            if (my == i + 1) {
                inMy = counter - 1;
            } else {
                inOther += counter;
            }
        }
        if (inOther + inMy < need) {
            out.println(-1);
        } else if (inOther < need) {
            out.println(1);
        } else {
            double result = 1;
            int a = inOther - need + 1;
            int b = inOther + inMy - need + 1;
            for (int i = 0; i < need; ++i) {
                result *= a;
                result /= b;
                a++;
                b++;
            }
            out.println(1 - result);
            // C(need, inOther) / C(need, inOther + inMy) =
            // = inOther! / need! / (inOther - need)! / (inOther + inMy)! * need! * (inOther + inMy - need)! =
            // = inOther! / (inOther - need)! / (inOther + inMy)! * (inOther + inMy - need)!
        }
    }
}

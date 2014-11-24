package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskI {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long now = in.nextInt();
        long init = in.nextInt();
        long diff = in.nextInt();
        int years = in.nextInt();
        int percents = in.nextInt();
        long first = now + init;
        double second = init;
        long cnt = 0;
        for (int i = 0; i < years; ++i) {
            init += diff;
            cnt += init;
        }
        first += cnt;
        second += cnt * (100 + percents) / 100.;
        // System.err.println(first + " " + second);
        if (first < second) {
            out.println("Cash");
            out.println(second - first);
        } else {
            out.println("Insurance");
            out.println(first - second);
        }
    }
}

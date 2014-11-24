package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        double probability = (100 - in.nextInt()) / 100.;
        String places = in.next();
        int my = -1;
        int before = 0;
        int after = 0;
        for (int i = 0; i < places.length(); ++i) {
            if (places.charAt(i) == '0') {
                if (my == -1) before++;
                else after++;
            } else if (places.charAt(i) == '2') {
                my = i;
            }
        }
        int init = my - before;

        double[][] dp = new double[before + 1][after + 1];
        for (double[] d : dp) Arrays.fill(d, counter);
        for (int free = 0; free <= before; ++free) {
            dp[free][after] = (before - free);
        }


        out.println(init + dp[before][after]);
    }
}

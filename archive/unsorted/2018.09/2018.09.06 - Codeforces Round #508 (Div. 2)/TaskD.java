package task;

import algoribrary.io.InputReader;
import net.egork.chelper.tester.Interactor;

import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        long negative = 0;
        long positive = 0;
        final int INF = Integer.MAX_VALUE;
        int minPositive = INF;
        int maxNegative = -INF;
        for (int i = 0; i < count; ++i) {
            int x = in.nextInt();
            if (x >= 0) {
                positive += x;
                minPositive = Math.min(minPositive, x);
            } else {
                negative -= x;
                maxNegative = Math.max(maxNegative, x);
            }
        }
        if (count == 1) {
            out.println(positive - negative);
        } else if (minPositive != INF && maxNegative != -INF) {
            out.println(negative + positive);
        } else if (minPositive == INF) {
            out.println(negative + 2 * maxNegative);
        } else {
            out.println(positive - 2 * minPositive);
        }
    }
}

package tasks;



import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        final int[] w = new int[counter];
        final int[] h = new int[counter];
        for (int i = 0; i < counter; ++i) {
            w[i] = in.nextInt();
            h[i] = in.nextInt();
        }
        int answer = Integer.MAX_VALUE;
        for (int hh = 0; hh <= 1000; ++hh) {
            int sum = 0;
            boolean ok = true;
            for (int j = 0; j < counter; ++j) {
                if (Math.min(w[j], h[j]) > hh) {
                    ok = false;
                    break;
                }
                if (Math.max(w[j], h[j]) <= hh) {
                    sum += Math.min(w[j], h[j]);
                } else {
                    sum += Math.max(w[j], h[j]);
                }
            }
            if (ok) answer = Math.min(answer, hh * sum);
        }
        if (answer == Integer.MAX_VALUE) throw new AssertionError();
        out.println(answer);
    }
}

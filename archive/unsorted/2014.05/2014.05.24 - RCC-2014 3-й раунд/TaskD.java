package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int sum = in.nextInt();
        int[] x = new int[counter];
        for (int i = 0; i < counter; i++) x[i] = in.nextInt();
        int[] answer = new int[counter];
        Arrays.fill(answer, 1);
        for (int i = counter; i > 0; i--) {
            sum -= x[counter - i] * i;
        }
        if (sum < 0) {
            out.println("NO");
            return;
        }
        int[] y = new int[counter];
        for (int i = 0; i < counter; i++) {
            y[i] = x[i];
            if (i > 0) y[i] += y[i - 1];
        }
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        int[] from = new int[sum + 1];
        for (int i = 0; i < counter; i++) {
            for (int w = 0; w <= sum - y[i]; w++) {
                if (dp[w]) {
                    dp[w + y[i]] = true;
                    from[w + y[i]] = i;
                }
            }
        }
        if (!dp[sum]) {
            out.println("NO");
        } else {
            out.println("YES");
            while (sum > 0) {
                answer[from[sum]]++;
                sum -= y[from[sum]];
            }
            for (int i = counter - 2; i >= 0; i--) answer[i] += answer[i + 1];
            print(out, counter, answer);
        }
    }

    private void print(PrintWriter out, int counter, int[] answer) {
        for (int j = 0; j < counter; j++) {
            out.print(answer[j]);
            out.print(" ");
        }
        out.println();
    }
}

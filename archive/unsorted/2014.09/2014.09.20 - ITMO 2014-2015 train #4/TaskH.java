package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskH {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long[] string = new long[300_000];
        int length = in.nextInt();
        int prev = in.nextInt();
        for (int i = 0; i < length; ++i) {
            string[i] = in.nextInt() - prev;
            prev += string[i];
        }
        string[length] = Integer.MAX_VALUE * 6L;
        int row = -1;
        int from = -1;
        long best = Integer.MAX_VALUE * 6L;
        int[] pf = new int[string.length];
        for (int i = 0; i < counter; ++i) {
            int len = in.nextInt();
            prev = in.nextInt();
            for (int j = 0; j < len; ++j) {
                string[j + length + 1] = in.nextInt() - prev;
                prev += string[j + length + 1];
            }
            if (len < length) continue;
            for (int j = 1; j < len + length + 1; ++j) {
                int k = pf[j - 1];
                while (k > 0 && string[j] - string[k] != string[j - 1] - string[k - 1]) {
                    k = pf[k - 1];
                }
                if (k == 0 || string[j] - string[k] == string[j - 1] - string[k - 1]) {
                    k++;
                }
                pf[j] = k;
                if (pf[j] == length) {
                    if (Math.abs(string[j] - string[k - 1]) < best) {
                        best = Math.abs(string[j] - string[k - 1]);
                        row = i + 1;
                        from = j - length - length;
                    }
                }
            }
        }
        out.print(row);
        if (row != -1) {
            out.print(" " + from);
        }
    }
}

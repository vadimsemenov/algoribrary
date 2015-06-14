package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        long number = in.nextLong();
        int[] cnt = new int[10];
        while (number > 0) {
            cnt[(int) (number % 10)]++;
            number /= 10;
        }
        if (cnt[9] > 0) {
            cnt[7] += 1 * cnt[9];
            cnt[3] += 2 * cnt[9];
            cnt[2] += 1 * cnt[9];
            cnt[9] = 0;
        }
        if (cnt[8] > 0) {
            cnt[2] += 3 * cnt[8];
            cnt[7] += cnt[8];
            cnt[8] = 0;
        }
        if (cnt[6] > 0) {
            cnt[3] += cnt[6];
            cnt[5] += cnt[6];
            cnt[6] = 0;
        }
        if (cnt[4] > 0) {
            cnt[3] += cnt[4];
            cnt[2] += 2 * cnt[4];
            cnt[4] = 0;
        }
        for (int i = 9; i > 1; --i) {
            for (int j = 0; j < cnt[i]; ++j) {
                out.print(i);
            }
        }
        out.println();
    }
}

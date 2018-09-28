package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        boolean[][] table = in.nextBooleanTable(2, length, '1');
        boolean[] fst = table[0];
        boolean[] snd = table[1];
        boolean[] or = new boolean[length];
        for (int i = 0; i < length; ++i) {
            or[i] = fst[i] | snd[i];
        }
        long onesCount = 0;
        for (int i = 0; i < length; ++i) {
            if (fst[i]) onesCount++;
        }
        long zeros = 0;
        for (int i = 0; i < length; ++i) {
            if (!or[i]) zeros++;
        }
        long ones = 0;
        for (int i = 0; i < length; ++i) {
            if (fst[i] && !snd[i]) ones++;
        }
        out.println(zeros * onesCount + ones * (length - onesCount) - zeros * ones);
    }
}

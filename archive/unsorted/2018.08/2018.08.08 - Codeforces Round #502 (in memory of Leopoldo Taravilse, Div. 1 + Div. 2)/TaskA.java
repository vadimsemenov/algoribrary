package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] sums = new int[count];
        for (int i = 0; i < count; ++i) {
            for (int j = 0; j < 4; ++j) {
                sums[i] += in.nextInt();
            }
        }
        int hisSum = sums[0];
        Arrays.sort(sums);
        int index = count - 1;
        while (sums[index] > hisSum) {
            index--;
        }
        out.println(count - index);
    }
}

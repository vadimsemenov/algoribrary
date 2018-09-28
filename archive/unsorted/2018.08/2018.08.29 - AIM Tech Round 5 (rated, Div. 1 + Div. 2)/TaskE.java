package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long[] array = in.nextLongArray(counter);
        int maxIdx = 0;
        boolean same = true;
        for (int i = 1; i < counter; ++i) {
            same &= array[i] == array[i - 1];
            if (array[i] > array[maxIdx]) {
                maxIdx = i;
            }
        }
        if (array[counter - 1] == array[maxIdx]) {
            maxIdx = counter - 1;
            while (maxIdx > 0 && array[maxIdx - 1] == array[maxIdx]) {
                maxIdx--;
            }
        }
        if (same) {
            if (array[maxIdx] == 0) {
                out.println("YES");
                for (int i = 0; i < counter; ++i) {
                    out.print("1 ");
                }
            } else {
                out.println("NO");
            }
            return;
        }
        long sum = 2 * array[maxIdx];
        for (int i = (maxIdx + counter - 1) % counter; i != maxIdx; i = (i + counter - 1) % counter) {
            sum += array[i];
            array[i] = sum;
        }
        out.println("YES");
        for (int i = 0; i < counter; ++i) {
            if (i != 0) out.print(' ');
            out.print(array[i]);
        }
    }
}

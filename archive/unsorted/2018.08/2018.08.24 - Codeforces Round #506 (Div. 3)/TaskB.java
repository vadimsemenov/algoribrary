package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] array = in.nextIntArray(count);
        int answer = 1;
        for (int i = 0; i < array.length; ) {
            int j = i + 1;
            while (j < array.length && array[j - 1] * 2 >= array[j]) {
                j++;
            }
            answer = Math.max(answer, j - i);
            i = j;
        }
        out.println(answer);
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB2 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        long number = in.nextLong() - 1;
        int[] answer = new int[length];
        int left = 0;
        int right = length - 1;
        for (int i = 0; i < length - 1; ++i) {
            long counter = 1L << (length - i - 2);
            if (number >= counter) {
                number -= counter;
                answer[right] = i + 1;
                right--;
            } else {
                answer[left] = i + 1;
                left++;
            }
        }
        answer[left] = length;
        for (int i = 0; i < length; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i]);
        }
        out.println();
    }
}

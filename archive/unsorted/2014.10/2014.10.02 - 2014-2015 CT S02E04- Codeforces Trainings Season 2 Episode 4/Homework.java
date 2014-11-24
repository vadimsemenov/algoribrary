package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Homework {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int toIncrement = in.nextInt();
        int[] array = new int[counter];
        int[] position = new int[1_000_000 + 1];
        int[] nextMin = new int[counter];
        int[] prevMin = new int[counter];
        for (int i = 0; i < counter; ++i) {
            array[i] = in.nextInt();
            position[array[i]] = i;
        }
        int[] stack = new int[counter];
        int top = 0;
        for (int i = 0; i < counter; ++i) {
            while (top > 0 && array[stack[top - 1]] > array[i]) {
                top--;
            }
            prevMin[i] = top == 0 ? -1 : stack[top - 1];
            stack[top++] = i;
        }
        top = 0;
        for (int i = counter - 1; i >= 0; --i) {
            while (top > 0 && array[stack[top - 1]] > array[i]) {
                top--;
            }
            nextMin[i] = top == 0 ? counter : stack[top - 1];
            stack[top++] = i;
        }
        long sum = 0;
        long[] incremented = new long[counter];
        for (int i = 0; i < counter; ++i) {
            int from = prevMin[i];
            int to = nextMin[i];
            long min = Math.min(i - from, to - i);
            long max = Math.max(i - from, to - i);
            long lens = min * max * (min + max) / 2;
            sum += lens * array[i];
            incremented[i] = lens;
        }
        Arrays.sort(incremented);
        for (int i = 0; i < toIncrement; ++i) {
            sum += incremented[counter - i - 1];
        }
        out.println(sum);
    }
}

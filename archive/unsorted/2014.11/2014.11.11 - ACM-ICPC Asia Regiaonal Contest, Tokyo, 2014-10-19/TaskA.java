package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int counter = in.nextInt();
        int[] initial = new int[length];
        for (int i = 0; i < length; ++i) {
            initial[i] = in.nextInt();
        }
        int[] need = new int[counter];
        for (int i = 0; i < counter; ++i) {
            need[i] = in.nextInt();
        }
        out.println(Math.min(solve(initial, generate(need, length, 0)), solve(initial, generate(need, length, 1))));
    }

    private int solve(int[] initial, int[] target) {
        int ones = 0;
        for (int i : initial) if (i == 1) ones++;
        for (int i : target) if (i == 1) ones--;
        if (ones != 0) return Integer.MAX_VALUE;
        int swaps = 0;
        for (int i = 0; i < initial.length; ++i) {
            if (initial[i] != target[i]) {
                int j = i;
                while (initial[i] != target[j]) {
                    ++j;
                }
                swaps += (j - i);
                swap(target, i, j);
            }
        }
        return swaps;
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private int[] generate(int[] need, int length, int bit) {
        int[] result = new int[length];
        int ptr = 0;
        for (int n : need) {
            for (int i = 0; i < n; ++i) {
                result[ptr++] = bit;
            }
            bit ^= 1;
        }
        return result;
    }
}

package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            int length = in.nextInt();
            char[] fst = in.next().toCharArray();
            char[] snd = in.next().toCharArray();
            int result = solve(fst, snd);
            out.println(result);
        }
    }

    private int solve(char[] array, char[] target) {
        int result = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != target[i]) {
                if (i + 1 == array.length) return -1;
                array[i] = (char) ('0' + '1' - array[i]);
                array[i + 1] = (char) ('0' + '1' - array[i + 1]);
                ++result;
            }
        }
        return result;
    }
}

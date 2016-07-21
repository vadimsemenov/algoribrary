package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int side = in.nextInt();
        int counter = in.nextInt();
        boolean[] left = new boolean[2 * side - 1];
        boolean[] right = new boolean[2 * side - 1];
        for (int i = 0; i < counter; ++i) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            left[x + y] = true;
            right[side - x - 1 + y] = true;
        }
//        for (boolean _ : left) System.err.print(_ ? 1 : 0);
//        System.err.println("");
//        for (boolean _ : right) System.err.print(_ ? 1 : 0);
        long answer = (long) side * side;
        int[] sub = new int[side];
        for (int i = 0; i < right.length; ++i) {
            if (right[i]) {
                answer -= (i > side - 1) ? (2 * side - i - 1) : (i + 1);
                sub[(i > side - 1) ? (2 * side - 1 - i - 1) : (i)]++;
            }
        }
//        System.err.println(Arrays.toString(sub));
        for (int i = side - 3; i >= 0; --i) {
            sub[i] += sub[i + 2];
        }
//        System.err.println(Arrays.toString(sub));
//        System.err.println(answer);
        for (int i = 0; i < left.length; ++i) {
            if (left[i]) {
                answer -= (i > side - 1) ? (2 * side - i - 1) : (i + 1);
                answer += sub[Math.max(i - side + 1, side - 1 - i)];
            }
        }
        out.println(answer);
    }
}

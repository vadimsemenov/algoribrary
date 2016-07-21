package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAndSurvivalGame {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] strength = in.nextIntArray(counter);
        boolean[] canSurvive = new boolean[counter];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < counter; ++i) {
            if (strength[i] > max) {
                max = strength[i];
                canSurvive[i] = true;
            }
        }
        max = Integer.MIN_VALUE;
        for (int i = counter - 1; i >= 0; --i) {
            if (strength[i] > max) {
                max = strength[i];
                canSurvive[i] = true;
            }
        }
        for (int i = 0; i < counter; ++i) if (canSurvive[i]) {
            out.print((i + 1) + " ");
        }
    }
}

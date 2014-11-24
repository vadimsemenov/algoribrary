package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] permutation = new int[counter];
        int inversionsCounter = 0;
        for (int i = 0; i < counter; ++i) {
            permutation[i] = in.nextInt();
            for (int j = 0; j < i; ++j) {
                if (permutation[j] > permutation[i]) {
                    inversionsCounter++;
                }
            }
        }
        out.println(inversionsCounter / 2 * 2 + inversionsCounter);
    }
}
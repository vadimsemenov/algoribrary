package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int min = in.nextInt();
        int max = in.nextInt();
        int minDiff = in.nextInt();
        int[] difficulty = new int[counter];
        for (int i = 0; i < counter; ++i) {
            difficulty[i] = in.nextInt();
        }
        int answer = 0;
        for (int mask = 1; mask < (1 << counter); ++mask) {
            int sum = 0;
            int minDifficulty = Integer.MAX_VALUE;
            int maxDifficulty = -1;
            for (int task = 0; task < counter; ++task) {
                if ((mask >>> task & 1) == 1) {
                    sum += difficulty[task];
                    minDifficulty = Math.min(minDifficulty, difficulty[task]);
                    maxDifficulty = Math.max(maxDifficulty, difficulty[task]);
                }
            }
            if (min <= sum && sum <= max && (maxDifficulty - minDifficulty) >= minDiff) {
                ++answer;
            }
        }
        out.println(answer);
    }
}

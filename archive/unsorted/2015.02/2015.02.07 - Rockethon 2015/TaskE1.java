package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE1 {
    private static final int MAX_VALUE = 10_000;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        final int MAX_SUM = length * MAX_VALUE;
        int counter = in.nextInt();
        int[] array = new int[length];
        for (int i = 0; i < length; ++i) {
            array[i] = in.nextInt();
        }
        int[][] layer = new int[length + 1][2 * MAX_SUM + 1];
        for (int[] l : layer) Arrays.fill(l, -1);
        layer[0][0 + MAX_SUM] = 0;
        int[][] nextLayer = new int[length + 1][2 * MAX_SUM + 1];
        for (int part = 0; part < counter; ++part) {
            for (int[] l : nextLayer) Arrays.fill(l, -1);
            for (int i = 0; i < length; ++i) {
                for (int last = 0; last < 2 * MAX_SUM + 1; ++last) {
                    if (layer[i][last] == -1) continue;
                    int current = 0;
                    for (int j = i; j < length; ++j) {
                        current += array[j];
                        nextLayer[j + 1][current + MAX_SUM] = part == 0 ? 0 :
                                Math.max(nextLayer[j + 1][current + MAX_SUM],
                                        layer[i][last] + Math.abs(current - (last - MAX_SUM)));
                    }
                }
            }
            int[][] tmp = layer;
            layer = nextLayer;
            nextLayer = tmp;
        }
        int answer = -1;
        for (int[] l : layer) for (int i : l) answer = Math.max(answer, i);
        out.println(answer);
    }
}

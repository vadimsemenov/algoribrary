package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    private static final int[][] D = new int[][] {
            new int[]{-1, 0, 1, 0},
            new int[]{0, -1, 0, 1}
    };

    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        int[][] coordinates = new int[qty][2];
        int[] min = new int[2];
        int[] max = new int[2];
        for (int i = 1; i < qty; ++i) {
            int parent = in.nextInt();
            int direction = in.nextInt();
            for (int d = 0; d < coordinates[i].length; ++d) {
                coordinates[i][d] = coordinates[parent][d] + D[d][direction];
                min[d] = Math.min(min[d], coordinates[i][d]);
                max[d] = Math.max(max[d], coordinates[i][d]);
            }
        }
        long answer = 0;

    }
}

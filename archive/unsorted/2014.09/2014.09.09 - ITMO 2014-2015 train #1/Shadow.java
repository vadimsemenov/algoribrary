package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Shadow {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[][] trees = new int[counter][2];
        for (int i = 0; i < counter; ++i) for (int j = 0; j < 2; ++j)
            trees[i][j] = in.nextInt();
        int w = in.nextInt();
        int h = in.nextInt();
        Arrays.sort(trees, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        for (int i = 0; i < counter; ++i) {
            if (trees[i][1] >= w + h) {
                if (i == counter - 1 || trees[i + 1][0] - trees[i][0] >= w) {
                    out.println("YES");
                    return;
                }
            }
        }
        out.println("NO");
    }
}

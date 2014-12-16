package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class MergeList {
    private static final int MODULO = 1_000_000_007;
    private static final int MAX = 200;
    private static final int[][] choose = new int[MAX + 1][];

    static {
        for (int i = 0; i <= MAX; ++i) {
            choose[i] = new int[i + 1];
            choose[i][0] = choose[i][i] = 1;
            for (int j = 1; j < i; ++j) {
                choose[i][j] = choose[i - 1][j - 1] + choose[i - 1][j];
                if (choose[i][j] > MODULO) choose[i][j] -= MODULO;
            }
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        if (n + m > MAX) throw new AssertionError(n + " " + m);
        out.println(choose[n + m][n]);
    }
}

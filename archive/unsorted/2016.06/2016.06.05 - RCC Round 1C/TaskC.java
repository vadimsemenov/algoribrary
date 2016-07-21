package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            int n = in.nextInt();
            int k = in.nextInt();
            int[][] answer = solve(n, k);
            for (int i = 0; i < n + 1; ++i) {
                out.println(answer[0][i] + " " + answer[1][i]);
            }
        }
    }

    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    private int[][] solve(int n, int k) {
        int qty = 0;
        int sum = 0;
        while (sum + qty < k) {
            sum += qty;
            ++qty;
        }
        int rem = k - sum;

        final int MAX = 3000;
        int x = 0;
        int y = 0;
        int d = 0;
        int lenx = qty + 1 + (rem > 0 ? 1 : 0);
        int leny = 1;
        int[][] result = new int[2][n + 1];
        boolean next = false;
        int step = 1;
        for (int i = 0; i < n + 1; ++i) {
            result[0][i] = x;
            result[1][i] = y;
            if (rem > 0 && i / 2 == rem && i % 2 == 0) {
                --lenx;
                next = true;
            }
            if (next && i % 2 == 0) {
                step = -1;
                next = false;
            }
            x += dx[d] * lenx;
            y += dy[d] * leny;
            if (d % 2 == 0) --lenx;
            else ++leny;
            d = (d + step) % 4;
        }
        int minx = 0;
        int miny = 0;
        for (int i = 0; i < n + 1; ++i) {
            minx = Math.min(minx, result[0][i]);
            miny = Math.min(miny, result[1][i]);
        }
        for (int i = 0; i < n + 1; ++i) {
            result[0][i] += minx + 1;
            result[1][i] += miny + 1;
            if (Math.max(result[0][i], result[1][i]) > MAX) throw new AssertionError(":(");
        }
        return result;
    }
}

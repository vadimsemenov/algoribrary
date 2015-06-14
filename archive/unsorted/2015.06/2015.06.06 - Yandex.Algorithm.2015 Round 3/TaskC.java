package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        boolean[][] table = new boolean[n + 2][m + 2];
        int counter = in.nextInt();
        int answer = 0;
        for (int i = 0; i < counter; ++i) {
            int x1 = in.nextInt() + 1;
            int y1 = in.nextInt() + 1;
            int x2 = in.nextInt() + 1;
            int y2 = in.nextInt() + 1;
            answer = Math.max(answer, (Math.abs(x1 - x2)) * (Math.abs(y1 - y2)));
            for (int x = Math.min(x1, x2) - 1; x <= Math.max(x1, x2); ++x) {
                for (int y = Math.min(y1, y2) - 1; y <= Math.max(y1, y2); ++y) {
                    table[x][y] = true;
                }
            }
        }
        int[] d = new int[m + 1];
        int[] d1 = new int[m + 1];
        int[] d2 = new int[m + 1];
        int[] stack = new int[m + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                if (table[i][j]) {
                    d[j] = i;
                }
            }
            int top = 0;
            for (int j = 1; j <= m; ++j) {
                while (top > 0 && d[stack[top - 1]] <= d[j]) {
                    --top;
                }
                d1[j] = top == 0 ? 0 : stack[top - 1];
                stack[top++] = j;
            }
            top = 0;
            for (int j = m; j > 0; --j) {
                while (top > 0 && d[stack[top - 1]] <= d[j]) {
                    --top;
                }
                d2[j] = top == 0 ? m + 1 : stack[top - 1];
                stack[top++] = j;
            }
            for (int j = 1; j <= m; ++j) {
                answer = Math.max(answer, (i - d[j]) * (d2[j] - d1[j] - 1));
            }
        }
        out.println(answer);
    }
}

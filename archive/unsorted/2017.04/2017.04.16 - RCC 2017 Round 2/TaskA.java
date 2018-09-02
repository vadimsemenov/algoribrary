package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        int[][] table = new int[rows][cols];
        int next = rows * cols;
        int diag = 0;
        while (next > 0) {
            for (int cc = 0; cc <= diag; ++cc) {
                int cr = diag - cc;
                if (isValidCell(cr, cc, rows, cols)) {
                    table[cr][cc] = next--;
                }
            }
            ++diag;
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                out.print(table[i][j]);
                out.print(' ');
            }
            out.println();
        }
    }

    private boolean isValidCell(int x, int y, int rows, int cols) {
        return 0 <= x && x < rows && 0 <= y && y < cols;
    }
}

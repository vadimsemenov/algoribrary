package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    final int[] dr = {0, 0, 1, -1};
    final int[] dc = {1, -1, 0, 0};
    private int columns, rows;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        rows = in.nextInt();
        columns = in.nextInt();
        boolean[] canDown = new boolean[columns];
        boolean[] canRight = new boolean[rows];
        String tmp = in.next();
        for (int i = 0; i < rows; ++i) {
            canRight[i] = tmp.charAt(i) == '>';
        }
        tmp = in.next();
        for (int i = 0; i < columns; ++i) {
            canDown[i] = tmp.charAt(i) == 'v';
        }
        boolean[][] used;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                used = new boolean[rows][columns];
                go(i, j, canDown, canRight, used);
                for (int x = 0; x < rows; ++x) {
                    for (int y = 0; y < columns; ++y) {
                        if (!used[x][y]) {
                            out.println("NO");
                            return;
                        }
                    }
                }
            }
        }
        out.println("YES");
    }

    private void go(int x, int y, boolean[] canDown, boolean[] canRight, boolean[][] used) {
        used[x][y] = true;
        if (canRight[x] && isValidCell(x, y + 1) && !used[x][y + 1]) {
            go(x, y + 1, canDown, canRight, used);
        }
        if (!canRight[x] && isValidCell(x, y - 1) && !used[x][y - 1]) {
            go(x, y - 1, canDown, canRight, used);
        }
        if (canDown[y] && isValidCell(x + 1, y) && !used[x + 1][y]) {
            go(x + 1, y, canDown, canRight, used);
        }
        if (!canDown[y] && isValidCell(x - 1, y) && !used[x - 1][y]) {
            go(x - 1, y, canDown, canRight, used);
        }
    }

    private boolean isValidCell(int x, int y) {
        return 0 <= x && x < rows && 0 <= y && y < columns;
    }
}

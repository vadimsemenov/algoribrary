package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    private int rows, cols;
    private char[][] table;

    public void solve(int __, InputReader in, PrintWriter out) {
        rows = in.nextInt();
        cols = in.nextInt();
        table = in.nextCharTable(rows, cols);
        for (int x = 0; x < rows; ++x) {
            for (int y = 0; y < cols; ++y) {
                if (table[x][y] != 'B') continue;
                for (int len = 0; len < Math.min(rows, cols); ++len) {
                    if (good(x, y, len) && bad(x, y, len + 1)) {
                        out.println((x + 1) + " " + (y + 1));
                        return;
                    }
                }
            }
        }
        throw new IllegalStateException("POLUNDRA");
    }

    private boolean good(int x, int y, int len) {
        return check(x - len, y) && check(x + len, y) && check(x, y - len) && check(x, y + len);
    }

    private boolean bad(int x, int y, int len) {
        return !check(x - len, y) && !check(x + len, y) && !check(x, y - len) && !check(x, y + len);
    }

    private boolean check(int x, int y) {
        return isValidCell(x, y) && table[x][y] == 'B';
    }

    private boolean isValidCell(int x, int y) {
        return 0 <= x && x < rows && 0 <= y && y < cols;
    }
}

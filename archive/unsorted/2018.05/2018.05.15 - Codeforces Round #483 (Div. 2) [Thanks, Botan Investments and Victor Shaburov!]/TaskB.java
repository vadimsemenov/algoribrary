package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    private static final String NO = "NO";
    private static final String YES = "YES";

    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        char[][] table = in.nextCharTable(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (table[i][j] != '*') {
                    int expected = table[i][j] == '.' ? 0 : table[i][j] - '0';
                    int actual = countBombs(i, j, table);
                    if (expected != actual) {
                        out.println(NO);
                        return;
                    }
                }
            }
        }
        out.println(YES);
    }

    private int countBombs(int x, int y, char[][] table) {
        int counter = 0;
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if ((i != x || j != y) && isValidCell(i, j, table) && table[i][j] == '*') {
                    ++counter;
                }
            }
        }
        return counter;
    }

    private boolean isValidCell(int i, int j, char[][] table) {
        return 0 <= i && i < table.length && 0 <= j && j < table[i].length;
    }
}

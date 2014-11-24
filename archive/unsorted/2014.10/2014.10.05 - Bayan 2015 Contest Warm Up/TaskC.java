package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
        }
        int topX = 0;
        int topY = 0;
        while (table[topX][topY] != 'X') {
            topY++;
            if (topY == columns) {
                topY = 0;
                topX++;
            }
        }
        if (topX > rows) throw new AssertionError("no interesting cells :(");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (table[i][j] == '.' && i + 1 < rows && j + 1 < columns && table[i + 1][j] == 'X' && table[i][j + 1] == 'X') {
                    out.println("NO");
                    return;
                }
                if (table[i][j] == '.' && i > 0 && j > 0 && table[i - 1][j] == 'X' && table[i][j - 1] == 'X') {
                    out.println("NO");
                    return;
                }
            }
        }

        int ver = 1;
        while (topX + ver <= rows && table[topX + ver - 1][topY] == 'X') {

        }
    }
}

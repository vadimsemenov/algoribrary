package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
        }
        boolean[][] bad = new boolean[rows + 1][columns + 1];
        bad[rows][columns] = true;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cnt = 0;
                if (i + 1 == rows || table[i][j] != table[i + 1][j]) ++cnt;
                if (j + 1 == columns || table[i][j] != table[i][j + 1]) ++cnt;
                if (cnt == 0 && table[i][j] != table[i + 1][j + 1]) cnt = 2;
                if (cnt == 1 && i + 1 < rows && j + 1 < columns && table[i][j] == table[i + 1][j + 1]) ++cnt;
                if (cnt == 2) bad[i + 1][j + 1] = true;
            }
        }

        int answer = 0;
        for (boolean[] b : bad) for (boolean bb : b) {
            if (bb) ++answer;
        }
        out.println(answer);
    }
}

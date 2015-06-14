package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int columns = in.nextInt();
        int rows = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
            if (table[i].length != columns) throw new AssertionError(table[i].length);
        }
        for (int j = 0; j < columns; ++j) {
            for (int k = 0; k < 2; ++k) {
                for (int i = 0; i < rows; ++i) {
                    for (int kk = 0; kk < 2; ++kk) {
                        out.print(table[i][j]);
                    }
                }
                out.println();
            }
        }
    }
}

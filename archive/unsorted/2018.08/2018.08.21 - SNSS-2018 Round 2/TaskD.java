package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int size = in.nextInt();
        int[][] table = in.nextIntTable(size, size);
        while (!correct(table)) {
            table = rotate(table);
        }
        for (int[] row : table) {
            for (int num : row) {
                out.print(num + " ");
            }
            out.println();
        }
    }

    private int[][] rotate(int[][] table) {
        int[][] result = new int[table.length][table.length];
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table.length; ++j) {
                result[table.length - 1 - j][i] = table[i][j];
            }
        }
        return result;
    }

    private boolean correct(int[][] table) {
        for (int[] worker : table) {
            for (int month = 1; month < worker.length; ++month) {
                if (worker[month - 1] >= worker[month]) {
                    return false;
                }
            }
        }
        for (int i = 1; i < table.length; ++i) {
            for (int month = 0; month < table.length; ++month) {
                if (table[i][month] <= table[i - 1][month]) {
                    return false;
                }
            }
        }
        return true;
    }
}

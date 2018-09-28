package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        int[] row = in.nextIntArray(rows);
        int[] col = in.nextIntArray(cols);
        int rowXor = 0;
        for (int r : row) rowXor ^= r;
        int colXor = 0;
        for (int c : col) colXor ^= c;
        if (rowXor != colXor) {
            out.println("NO");
            return;
        }
        out.println("YES");
        int[][] matrix = new int[rows][cols];

        for (int i = 1; i < rows; ++i) {
            matrix[i][0] = row[i];
        }
        for (int j = 1; j < cols; ++j) {
            matrix[0][j] = col[j];
        }
        matrix[0][0] = rowXor ^ col[0] ^ row[0];
        assertRows(rows, cols, row, matrix);
        assertCols(rows, cols, col, matrix);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                out.print(matrix[i][j] + " ");
            }
            out.println();
        }
    }

    private void assertCols(int rows, int cols, int[] col, int[][] matrix) {
        for (int j = 0; j < cols; ++j) {
            int xor = 0;
            for (int i = 0; i < rows; ++i) {
                xor ^= matrix[i][j];
            }
            if (xor != col[j]) {
                throw new AssertionError(xor + " " + j);
            }
        }
    }

    private void assertRows(int rows, int cols, int[] row, int[][] matrix) {
        for (int i = 0; i < rows; ++i) {
            int xor = 0;
            for (int j = 0; j < cols; ++j) {
                xor ^= matrix[i][j];
            }
            if (xor != row[i]) {
                throw new AssertionError(xor + " " + i);
            }
        }
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        int counter = in.nextInt();
        int[] initial = new int[rows * columns];
        int[][] aux = new int[rows][columns];
        for (int i = 0, foo = 0; i < rows; ++i) for (int j = 0; j < columns; ++j) {
            aux[i][j] = foo++;
        }
        for (int q = 0; q < counter; ++q) {
            int type = in.nextInt();
            if (type == 1) {
                int row = in.nextInt() - 1;
                int first = aux[row][0];
                for (int j = 0; j + 1 < columns; ++j) {
                    aux[row][j] = aux[row][j + 1];
                }
                aux[row][columns - 1] = first;
            } else if (type == 2) {
                int column = in.nextInt() - 1;
                int first = aux[0][column];
                for (int i = 0; i + 1 < rows; ++i) {
                    aux[i][column] = aux[i + 1][column];
                }
                aux[rows - 1][column] = first;
            } else if (type == 3) {
                int row = in.nextInt() - 1;
                int column = in.nextInt() - 1;
                initial[aux[row][column]] = in.nextInt();
            } else throw new AssertionError(type);
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                out.print(initial[i * columns + j] + " ");
            }
            out.println();
        }
    }
}

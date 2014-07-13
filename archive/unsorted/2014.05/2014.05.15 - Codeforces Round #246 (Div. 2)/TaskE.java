package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        int[][] table = new int[rows][columns];
        for (int i = 0; i < rows; i++) Arrays.fill(table[i], -1);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (table[i][j] == -1) {
                    int minColor = -1;
                    while (true) {
                        minColor++;
                        if (i - 1 >= 0 && table[i - 1][j] == minColor) continue;
                        if (j - 1 >= 0 && table[i][j - 1] == minColor) continue;
                        if (j + 1 < columns && table[i][j + 1] == minColor) continue;
                        break;
                    }
                    int color = minColor;
                    int size;
                    for (size = 1; j + size < columns && i + size < rows; size++) {
                        if (table[i][j + size] != -1) break;
                        minColor = 0;
                        if (i - 1 >= 0 && table[i - 1][j + size] == minColor) minColor++;
                        if (j + 1 + size < columns && table[i][j + 1 + size] == minColor) minColor++;
                        if (i - 1 >= 0 && table[i - 1][j + size] == minColor) minColor++;
                        if (minColor != color) break;
                    }

                    for (int row = i; row < i + size; row++)
                        for (int column = j; column < j + size; column++)
                            table[row][column] = color;

                    j += size - 1;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                out.print((char) (table[i][j] + 'A'));
            out.println();
        }
    }
}

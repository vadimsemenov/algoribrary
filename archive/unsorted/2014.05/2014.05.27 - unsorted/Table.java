package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Table {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; i++) table[i] = in.next().toCharArray();
        int[][] maxDown = new int[rows][columns];
        for (int j = 0; j < columns; j++) {
            for (int i = rows - 1; i >= 0; i--) {
                if (table[i][j] == 'X') {
                    maxDown[i][j] = -1;
                } else {
                    if (i < rows - 1 && maxDown[i + 1][j] != -1) {
                        maxDown[i][j] = 1 + maxDown[i + 1][j];
                    } else {
                        maxDown[i][j] = 1;
                    }
                }
            }
        }
        int answer = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (table[i][j] == '.') {
                    int k = j;
                    int height = maxDown[i][j];
                    int current = -1;
                    do {
                        height = Math.min(height, maxDown[i][k]);
                        current = Math.max(current, 2 * (height + (k - j + 1)));
                        k++;
                    } while (k < columns && table[i][k] == '.');
                    answer = Math.max(answer, current);
                }
            }
        }
        if (answer == -1) answer = 1;
        out.println(answer - 1);
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Debug {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; i++)
            table[i] = in.next().toCharArray();
        boolean[][][] can = new boolean[rows][columns][Math.max(rows, columns) + 1];
        int[][] max = new int[rows][columns];

        int answer = 1;
        for (int i = rows - 1; i >= 0; i--) for (int j = columns - 1; j >= 0; j--) {
            can[i][j][1] = true;
            max[i][j] = 1;
            if (i < rows - 1 && j < columns - 1) {
                if (table[i][j] == table[i + 1][j + 1]  && table[i][j + 1] == table[i + 1][j]) {
                    can[i][j][2] = true;
                    max[i][j] = 2;
                }
            }
            if (i < rows - 1 && j < columns - 1) {
                for (int size = max[i + 1][j + 1] + 2; size > 2; size --) {
                    if (!can[i + 1][j + 1][size - 1]) continue;
                    can[i][j][size] = true;
                    for (int x = 0; x < size; x++) {
                        can[i][j][size] &= table[i + x][j] == table[i + size - x - 1][j + size - 1];
                    }
                    for (int y = 0; y < size; y++) {
                        can[i][j][size] &= table[i][j + y] == table[i + size - 1][j + size - y - 1];
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++) for (int j = 0; j < columns; j++) for (int size = 1; size < Math.min(rows - i, columns - j); size++) if (can[i][j][size]) answer = Math.max(answer, size);
        out.println(answer);
    }
}


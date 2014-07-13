package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    private int rows, columns;
    private int[][] array;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        rows = in.nextInt();
        columns = in.nextInt();
        array = new int[rows][columns];
        for (int i = 0; i < rows; i++) for (int j = 0; j < columns; j++)
            array[i][j] = in.nextInt();
        int[][] first = new int[rows][columns];
        int[][] firstRev = new int[rows][columns];
        int[][] second = new int[rows][columns];
        int[][] secondRev = new int[rows][columns];
        fill(first,     0,        0,            1, 1);
        fill(firstRev,  rows - 1, columns - 1, -1, -1);
        fill(second,    rows - 1, 0,           -1, 1);
        fill(secondRev, 0,        columns - 1,  1, -1);
        int result = -1;
        for (int i = 1; i + 1 < rows; i++) for (int j = 1; j + 1 < columns; j++) {
            int here = Math.max(
                    first[i - 1][j] + firstRev[i + 1][j] + second[i][j - 1] + secondRev[i][j + 1],
                    first[i][j - 1] + firstRev[i][j + 1] + second[i + 1][j] + secondRev[i - 1][j]
                    );
            result = Math.max(result, here);
        }
        out.println(result);
    }

    private void fill(int[][] fill, int startX, int startY, int dx, int dy) {
        fill[startX][startY] = array[startX][startY];
        for (int x = startX; x >= 0 && x < rows; x += dx) {
            for (int y = startY; y >= 0 && y < columns; y += dy) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < rows) {
                    fill[nx][y] = Math.max(fill[nx][y], fill[x][y] + array[nx][y]);
                }
                if (ny >= 0 && ny < columns) {
                    fill[x][ny] = Math.max(fill[x][ny], fill[x][y] + array[x][ny]);
                }
            }
        }
    }
}

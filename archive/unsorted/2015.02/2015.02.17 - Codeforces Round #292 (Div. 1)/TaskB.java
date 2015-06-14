package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    private static final String NOT_UNIQUE = "Not unique";
    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    private char[][] table;
    private int[][] queue;
    private int head, tail;
    private boolean[][] was;
    private int rows, columns;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        rows = in.nextInt();
        columns = in.nextInt();
        table = new char[rows][];
        queue = new int[rows * columns][2];
        was = new boolean[rows][columns];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cnt = checkCell(i, j);
                if (cnt == 4) {
                    out.println(NOT_UNIQUE);
                    return;
                }
                if (cnt == 3) {
                    add(i, j);
                }
            }
        }
        while (head < tail) {
            int x = queue[head][0];
            int y = queue[head][1];
            head++;
            if ("<>^v".indexOf(table[x][y]) != -1) continue;
            int nx = -1;
            int ny = -1;
            for (int d = 0; d < 4; ++d) {
                if (isValidCell(x + dx[d], y + dy[d]) && table[x + dx[d]][y + dy[d]] == '.') {
                    if (nx != -1 || ny != -1) {
                        throw new AssertionError(x + " " + y);
                    }
                    nx = x + dx[d];
                    ny = y + dy[d];
                }
            }
            if (nx == -1 || ny == -1) {
                out.println(NOT_UNIQUE);
                return;
            }
            if (nx == x) {
                table[x][Math.min(y, ny)] = '<';
                table[x][Math.max(y, ny)] = '>';
            } else if (ny == y) {
                table[Math.min(x, nx)][y] = '^';
                table[Math.max(x, nx)][y] = 'v';
            } else throw new AssertionError(x + " " + y + " " + nx + " " + ny + " " + checkCell(x, y));
            for (int d = 0; d < 4; ++d) {
                int cnt = checkCell(nx + dx[d], ny + dy[d]);
                if (cnt == 4) {
                    out.println(NOT_UNIQUE);
                    return;
                }
                if (cnt == 3) {
                    add(nx + dx[d], ny + dy[d]);
                }
            }
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (table[i][j] == '.') {
                    out.println(NOT_UNIQUE);
                    return;
                }
            }
        }
        for (int i = 0; i < rows; ++i) {
            out.println(String.valueOf(table[i]));
        }
    }

    private void add(int i, int j) {
        if (was[i][j]) return;
        was[i][j] = true;
        queue[tail][0] = i;
        queue[tail][1] = j;
        tail++;
    }

    private int checkCell(int x, int y) {
        int cnt = 0;
        if (isValidCell(x, y) && table[x][y] == '.') {
            for (int d = 0; d < 4; ++d) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (!isValidCell(nx, ny) || table[nx][ny] != '.') {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private boolean isValidCell(int nx, int ny) {
        return 0 <= nx && nx < rows && 0 <= ny && ny < columns;
    }
}

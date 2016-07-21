package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    private static final int[] dx = {0, -1, 0, 1};
    private static final int[] dy = {-1, 0, 1, 0};
    private char[][] answer;
    private char[][] table;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        table = new char[3][];
        answer = new char[3][];
        for (int i = 0; i < 3; ++i) {
            table[i] = in.next().toCharArray();
            answer[i] = table[i].clone();
        }
        dfs(1, 1, 1);
        for (char[] row : answer) out.println(String.valueOf(row));
    }

    private boolean dfs(int row, int col, int id) {
        if ((answer[row][col] != '?' && answer[row][col] != '0' + id) ||
                (table[row][col] != '?' && table[row][col] != id + '0')) {
            return false;
        }
        answer[row][col] = (char) ('0' + id);
        if (id == 9) return true;
        for (int d = 0; d < 4; ++d) {
            int nx = row + dx[d];
            int ny = col + dy[d];
            if (isValidCell(nx, ny) && dfs(nx, ny, id + 1)) {
                return true;
            }
        }
        answer[row][col] = table[row][col];
        return false;
    }

    private boolean isValidCell(int nx, int ny) {
        return 0 <= nx && nx < 3 && 0 <= ny && ny < 3;
    }
}

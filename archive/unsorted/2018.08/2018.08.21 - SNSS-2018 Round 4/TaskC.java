package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    private char[][] table;
    private int[][] answer;

    public void solve(int __, InputReader in, PrintWriter out) {
        table = in.nextCharTable(8, 8);
        answer = new int[8][8];
        int count = in.nextInt();
        for (int i = 0; i < count; ++i) {
            char[] word = in.next().toCharArray();
            filling:
            for (int r = 0; r < table.length; ++r) {
                for (int c = 0; c < table[r].length; ++c) {
                    if (answer[r][c] == 0 && fill(r, c, word, 0, i + 1)) {
                        break filling;
                    }
                }
            }
        }
        for (int[] row : answer) {
            for (int id : row) {
                out.print(id + " ");
            }
            out.println();
        }
    }

    private static final int[] dx = new int[]{0, 1, 0, -1};
    private static final int[] dy = new int[]{1, 0, -1, 0};

    private boolean fill(int r, int c, char[] word, int position, int id) {
        if (table[r][c] != word[position]) return false;
        answer[r][c] = id;
        if (position + 1 == word.length) return true;
        for (int dir = 0; dir < 4; ++dir) {
            int x = r + dx[dir];
            int y = c + dy[dir];
            if (isValidCell(x, y) && answer[x][y] == 0 && fill(x, y, word, position + 1, id)) {
                return true;
            }
        }
        answer[r][c] = 0;
        return false;
    }

    private boolean isValidCell(int x, int y) {
        return 0 <= x && x < 8 && 0 <= y && y < 8;
    }
}

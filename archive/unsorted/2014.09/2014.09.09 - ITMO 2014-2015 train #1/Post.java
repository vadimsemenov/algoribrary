package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Post {
    static final int INF = Integer.MAX_VALUE / 10;
    static final int[] dx = new int[]{1, 0, -1, 0};
    static final int[] dy = new int[]{0, 1, 0, -1};

    int rows, columns;

    int[][][] fromLetter = new int[26][][];
    int[][] fromLetterToLetter = new int[26][26];

    char[][] table;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        rows = in.nextInt();
        columns = in.nextInt();
        for (int[] d : fromLetterToLetter) Arrays.fill(d, INF);
        for (int i = 0; i < fromLetter.length; ++i) {
            fromLetter[i] = new int[rows][columns];
            for (int[] d : fromLetter[i]) Arrays.fill(d, INF);
            fromLetterToLetter[i][i] = 1;
        }
        table = new char[rows][];
        for (int r = 0; r < rows; r++) table[r] = in.next().toCharArray();
        for (int letter = 0; letter < 26; ++letter) {
            int[][] queue = new int[rows * columns][2];
            int head = 0;
            int tail = 0;
            for (int r = 0; r < rows; ++r) for (int c = 0; c < columns; ++c) {
                if (table[r][c] == letter + 'a') {
                    fromLetter[letter][r][c] = 0;
                    queue[tail][0] = r;
                    queue[tail][1] = c;
                    tail++;
                }
            }
            while (head < tail) {
                int pr = queue[head][0];
                int pc = queue[head][1];
                head++;
                for (int d = 0; d < 4; ++d) {
                    int nr = pr + dx[d];
                    int nc = pc + dy[d];
                    if (!isValidCell(nr, nc)) continue;
                    if (fromLetter[letter][nr][nc] > fromLetter[letter][pr][pc] + 1) {
                        fromLetter[letter][nr][nc] = fromLetter[letter][pr][pc] + 1;
                        queue[tail][0] = nr;
                        queue[tail][1] = nc;
                        tail++;
                        if (table[nr][nc] != '.') {
                            fromLetterToLetter[letter][table[nr][nc] - 'a'] = Math.min(
                                    fromLetterToLetter[letter][table[nr][nc] - 'a'],
                                    fromLetter[letter][nr][nc] + 1
                            );
                        }
                    }
                }
            }
        }
        for (int k = 0; k < 26; ++k) {
            for (int i = 0; i < 26; ++i) {
                for (int j = 0; j < 26; ++j) {
                    fromLetterToLetter[i][j] = Math.min(
                            fromLetterToLetter[i][j],
                            fromLetterToLetter[i][k] + fromLetterToLetter[k][j]
                    );
                }
            }
        }
        int queries = in.nextInt();
        for (int query = 0; query < queries; ++query) {
            int x1 = in.nextInt() - 1;
            int y1 = in.nextInt() - 1;
            int x2 = in.nextInt() - 1;
            int y2 = in.nextInt() - 1;
            int answer = Math.abs(x1 - x2) + Math.abs(y1 - y2);
            for (int i = 0; i < 26; ++i) for (int j = 0; j < 26; ++j) {
                int d1 = fromLetter[i][x1][y1];
                int d2 = fromLetter[j][x2][y2];
                int d = d1 + d2 + fromLetterToLetter[i][j];
                if (i != j) d++;
                answer = Math.min(answer, d);
            }
            out.print(answer);
            out.print(' ');
        }
        out.println();
    }

    private boolean isValidCell(int r, int c) {
        return 0 <= r && r < rows && 0 <= c && c < columns;
    }
}

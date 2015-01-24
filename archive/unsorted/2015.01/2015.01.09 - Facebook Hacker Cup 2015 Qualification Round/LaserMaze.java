package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LaserMaze {
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, -1, 0, 1};

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
        }
        int startX = -1;
        int startY = -1;
        int goalX = -1;
        int goalY = -1;
        int[][][] field = new int[4][rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                for (int k = 0; k < 4; ++k) {
                    if (table[i][j] == 'S') {
                        startX = i;
                        startY = j;
                        if (field[k][i][j] == 0) field[k][i][j] = 1;
                    } else if (table[i][j] == 'G') {
                        goalX = i;
                        goalY = j;
                        if (field[k][i][j] == 0) field[k][i][j] = 1;
                    } else if (table[i][j] == '.') {
                        if (field[k][i][j] == 0) field[k][i][j] = 1;
                    } else if (table[i][j] == '#') {
                        field[k][i][j] = 2;
                    } else {
                        int d = "v<^>".indexOf(table[i][j]);
                        assert d != -1 : table[i][j];
                        d = (d + k) % 4;
                        int x = i;
                        int y = j;
                        do {
                            field[k][x][y] = 2;
                            x += dx[d];
                            y += dy[d];
                        } while (0 <= x && x < rows && 0 <= y && y < columns && (".SG".indexOf(table[x][y]) != -1));
                    }
                }
            }
        }
        assert startX != -1 && startY != -1 && goalX != -1 && goalY != -1;

        List<State> bfs = new ArrayList<>();
        int ptr = 0;
        bfs.add(new State(0, startX, startY));
        while (ptr < bfs.size()) {
            State current = bfs.get(ptr++);
            for (int d = 0; d < 4; ++d) {
                int nx = current.x + dx[d];
                int ny = current.y + dy[d];
                if (0 <= nx && nx < rows && 0 <= ny && ny < columns &&
                        field[(current.distance + 1) % 4][nx][ny] == 1) {
                    if (nx == goalX && ny == goalY) {
                        print(out, testNumber, Integer.toString(current.distance + 1));
                        return;
                    }
                    field[(current.distance + 1) % 4][nx][ny] = 3;
                    bfs.add(new State(current.distance + 1, nx, ny));
                }
            }
        }
        print(out, testNumber, "impossible");
    }

    private void print(PrintWriter out, int testNumber, String answer) {
               out.println("Case #" + testNumber + ": " + answer);
        System.err.println("Case #" + testNumber + ": " + answer);
    }

    private static class State {
        int distance, x, y;

        public State(int distance, int x, int y) {
            this.distance = distance;
            this.x = x;
            this.y = y;
        }
    }
}

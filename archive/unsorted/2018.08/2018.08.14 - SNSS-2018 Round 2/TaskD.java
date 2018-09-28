package task;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    private static final int[][] D = new int[][] {
            new int[]{0, -1, 0, 1},
            new int[]{-1, 0, 1, 0}
    };
    private static final int INF = Integer.MAX_VALUE / 3;

    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        char[][] scheme = in.nextCharTable(rows, cols);
        boolean[][] visited = new boolean[rows][cols];
        int[][] queue = new int[rows * cols][2];
        int head = 0;
        int tail = 0;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (scheme[r][c] == 'S') {
                    queue[tail][0] = r;
                    queue[tail][1] = c;
                    tail++;
                }
            }
        }
        assert tail == 1;
        int[][] distance = new int[rows][cols];
        for (int[] d : distance) {
            Arrays.fill(d, INF);
        }
        distance[queue[0][0]][queue[0][1]] = 0;
        int[] next = new int[2];
        while (head < tail) {
            int[] position = queue[head++];
            for (int dir = 0; dir < 4; ++dir) {
                for (int dim = 0; dim < 2; ++dim) {
                    next[dim] = position[dim] + D[dim][dir];
                }
                if (move(next, scheme, visited)) {
                    if (distance[next[0]][next[1]] == INF && valid(next, scheme)) {
                        distance[next[0]][next[1]] = distance[position[0]][position[1]] + 1;
                        queue[tail][0] = next[0];
                        queue[tail][1] = next[1];
                        tail++;
                    }
                }
            }
        }
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (scheme[r][c] == '.') {
                    int d = distance[r][c];
                    out.println(d < INF ? d : -1);
                }
            }
        }
    }

    private boolean valid(int[] position, char[][] scheme) {
        for (int step = -1; step < 2; step += 2) {
            for (int c = position[1]; scheme[position[0]][c] != 'W'; c += step) {
                if (scheme[position[0]][c] == 'C') {
                    return false;
                }
            }
            for (int r = position[0]; scheme[r][position[1]] != 'W'; r += step) {
                if (scheme[r][position[1]] == 'C') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean move(int[] position, char[][] scheme, boolean[][] visited) {
        int dir;
        while (!visited[position[0]][position[1]] && (dir = "LURD".indexOf(scheme[position[0]][position[1]])) >= 0) {
            visited[position[0]][position[1]] = true;
            for (int dim = 0; dim < 2; ++dim) {
                position[dim] += D[dim][dir];
            }
        }
        return scheme[position[0]][position[1]] == '.';
    }
}

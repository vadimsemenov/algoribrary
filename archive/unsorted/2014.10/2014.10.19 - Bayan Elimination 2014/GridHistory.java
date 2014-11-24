package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class GridHistory {
    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        out.println("Case #" + testNumber + ":");
        System.err.println("proceed test #" + testNumber);
        int rows = in.nextInt();
        final int columns = in.nextInt();
        final int[][] table = new int[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                table[i][j] = in.nextInt();
            }
        }
        Integer[] order = new Integer[rows * columns];
        for (int i = 0; i < rows * columns; ++i) {
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int x1 = o1 / columns;
                int y1 = o1 % columns;
                int x2 = o2 / columns;
                int y2 = o2 % columns;
                return table[x2][y2] - table[x1][y1];
            }
        });
        int[] distance = new int[rows * columns];
        int[][] queue = new int[rows * columns][2];
        int head, tail;
        System.err.println(Arrays.toString(order));
        for (int i = 0; i < order.length - 1; ++i) {
            int x = order[i] / columns;
            int y = order[i] % columns;
            int nx = order[i + 1] / columns;
            int ny = order[i + 1] % columns;
            Arrays.fill(distance, -1);
            distance[x * columns + y] = 0;
            int minNumber = table[nx][ny];
            head = 0; tail = 0;

            queue[tail][0] = x;
            queue[tail][1] = y;
            tail++;

            boolean canWalk = false;
            bfs:
            while (head < tail) {
                int cx = queue[head][0];
                int cy = queue[head][1];
                head++;
                for (int d = 0; d < 4; ++d) {
                    int xn = cx + dx[d];
                    int yn = cy + dy[d];
                    if (xn == nx && yn == ny) {
                        if (distance[cx * columns + cy] + 1 <= table[x][y] - table[nx][ny]) {
                            distance[nx * columns + ny] = distance[cx * columns + cy] + 1;
                        }
                        for (int dd = 0; dd < 4; ++ dd) {
                            int xnn = xn + dx[dd];
                            int ynn = xn + dy[dd];
                            if (0 <= xnn && xnn < rows && 0 <= ynn && ynn < columns &&
                                    xnn != x && ynn != y &&
                                    table[xnn][ynn] >= minNumber) {
                                canWalk = true;
                            }
                        }
                        if (distance[nx * columns + ny] > 1 || canWalk) break bfs;
                    }
                    if (0 <= xn && xn < rows && 0 <= yn && yn < columns &&
                            table[xn][yn] >= minNumber &&
                            distance[xn * columns + yn] == -1) {
                        if (cx == x && cy == y) {
                            canWalk = true;
                        }
                        distance[xn * columns + yn] = distance[cx * columns + cy] + 1;
                        queue[tail][0] = xn;
                        queue[tail][1] = yn;
                        tail++;
                    }
                }
            }

            if (distance[nx * columns + ny] == -1 ||
                    (table[x][y] - table[nx][ny]) % 2 != distance[nx * columns + ny] % 2 ||
                    (!canWalk && (table[x][y] - table[nx][ny]) > 1 && distance[nx * columns + ny] == 1)) {
                System.err.println("(" + x + ", " + y + ") -X-> (" + nx + ", " + ny + ")");
                System.err.println(distance[nx * columns + ny]);
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }
}

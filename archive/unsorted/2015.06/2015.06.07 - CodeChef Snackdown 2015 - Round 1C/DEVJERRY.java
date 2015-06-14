package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class DEVJERRY {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int size = in.nextInt();
        int[][] distance = new int[size][size];
        for (int[] d : distance) Arrays.fill(d, -1);
        int sx = in.nextInt() - 1;
        int sy = in.nextInt() - 1;
        int ex = in.nextInt() - 1;
        int ey = in.nextInt() - 1;
        int bx = in.nextInt() - 1;
        int by = in.nextInt() - 1;
        distance[sx][sy] = 0;
        int[][] queue = new int[size * size][2];
        int head = 0;
        int tail = 0;
        {
            queue[tail][0] = sx;
            queue[tail][1] = sy;
            ++tail;
        }
        while (head < tail) {
            int cx = queue[head][0];
            int cy = queue[head][1];
            ++head;
            for (int d = 0; d < 4; ++d) {
                int xx = cx + new int[]{1, 0, -1, 0}[d];
                int yy = cy + new int[]{0, 1, 0, -1}[d];
                if (0 <= xx && xx < size && 0 <= yy && yy < size &&
                        !(xx == bx && yy == by) && distance[xx][yy] == -1) {
                    distance[xx][yy] = distance[cx][cy] + 1;
                    {
                        queue[tail][0] = xx;
                        queue[tail][1] = yy;
                        ++tail;
                    }
                }
            }
        }
        out.println(distance[ex][ey]);
    }
}

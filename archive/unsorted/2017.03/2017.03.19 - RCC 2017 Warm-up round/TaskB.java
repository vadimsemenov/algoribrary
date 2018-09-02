package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int counter = in.nextInt();
        List<Integer> red = new ArrayList<>();
        List<Integer> blue = new ArrayList<>();
        List<Integer> black = new ArrayList<>();
        List<Integer> yellow = new ArrayList<>();
        List<Integer> rose = new ArrayList<>();
        int minLeft = 1;
        int minRight = 1;
        int maxLeft = rows;
        int maxRight = rows;
        int up = counter + 1;
        boolean[][] head = new boolean[2][up];
        boolean[][] tail = new boolean[2][up];
        for (int id = 1; id <= counter; ++id) {
            int row = in.nextInt();
            int col = in.nextInt();
            if (row == Math.min(minLeft, minRight)) {
                if (minLeft == minRight) {
                    if (col == 1) red.add(id);
                    else blue.add(id);
                } else {
                    red.add(id);
                    blue.add(id);
                }
            }
            if (row == Math.max(maxLeft, maxRight)) {
                if (maxLeft == maxRight) {
                    if (col == 1) black.add(id);
                    else yellow.add(id);
                } else {
                    black.add(id);
                    yellow.add(id);
                }
            }
            rose.add(id);

            if (row <= up) head[col - 1][row - 1] = true;
            if (row > rows - up) tail[col - 1][rows - row] = true;
            while (head[0][minLeft - 1]) ++minLeft;
            while (head[1][minRight - 1]) ++minRight;
            while (tail[0][rows - maxLeft]) --maxLeft;
            while (tail[1][rows - maxRight]) --maxRight;
        }

        print(out, red);
        print(out, blue);
        print(out, black);
        print(out, yellow);
        print(out, rose);
    }

    private void print(PrintWriter out, List<Integer> array) {
        out.print(array.size());
        for (int i : array) {
            out.print(' ');
            out.print(i);
        }
        out.println();
    }
}

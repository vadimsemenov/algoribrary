package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class GoGopher {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int area = in.nextInt();
        int rows = 3;
        int cols = (area + 2) / 3;
        for (int i = 3; i < (area + 2) / 3; ++i) {
            int j = (area + i - 1) / i;
            if (i + j < rows + cols) {
                rows = i;
                cols = j;
            }
        }
        assert rows * cols >= area;
        boolean[][] prepared = new boolean[rows + 1][cols + 1];
        while (true) {
            int nextX = 2;
            int nextY = 2;
            int bestWeight = 0;
            nextStep:
            for (int x = 2; x < rows; ++x) {
                for (int y = 2; y < cols; ++y) {
                    int currentWeight = 0;
                    for (int xx = x - 1; xx <= x + 1; ++xx) {
                        for (int yy = y - 1; yy <= y + 1; ++yy) {
                            if (!prepared[xx][yy]) {
                                ++currentWeight;
                            }
                        }
                    }
                    if (currentWeight > bestWeight) {
                        nextX = x;
                        nextY = y;
                        bestWeight = currentWeight;
                        if (currentWeight == 9) {
                            break nextStep;
                        }
                    }
                }
            }
            out.println(nextX + " " + nextY);
            out.flush();
            int x = in.nextInt();
            int y = in.nextInt();
            if (x == 0 && y == 0) {
                return; // Success
            } else if (x < 0 && y < 0) {
                return; // Fail
            }
            prepared[x][y] = true;
        }
    }
}

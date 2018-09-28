package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[][] rectangles = in.nextIntTable(counter, 4);
        int[] x1 = findMax(rectangles, 0);
        int[] y1 = findMax(rectangles, 1);
        int[] x2 = findMin(rectangles, 2);
        int[] y2 = findMin(rectangles, 3);
        for (int x : union(x1, x2)) {
            for (int y : union(y1, y2)) {
                if (good(x, y, rectangles)) {
                    out.println(x + " " + y);
                    return;
                }
            }
        }
        throw new IllegalStateException("POLUNDRA");
    }

    private boolean good(int x, int y, int[][] rectangles) {
        int count = 0;
        for (int[] rectangle : rectangles) {
            if (inside(x, y, rectangle)) {
                count++;
            }
        }
        return count + 1 >= rectangles.length;
    }

    private boolean inside(int x, int y, int[] rectangle) {
        return rectangle[0] <= x && x <= rectangle[2] &&
                rectangle[1] <= y && y <= rectangle[3];
    }

    private int[] union(int[] fst, int[] snd) {
        int[] result = new int[fst.length + snd.length];
        System.arraycopy(fst, 0, result, 0, fst.length);
        System.arraycopy(snd, 0, result, fst.length, snd.length);
        return result;
    }

    private int[] findMax(int[][] rectangles, int coordinate) {
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        for (int[] rectangle : rectangles) {
            if (rectangle[coordinate] > max) {
                secondMax = max;
                max = rectangle[coordinate];
            } else {
                secondMax = Math.max(secondMax, rectangle[coordinate]);
            }
        }
        return new int[]{ max, secondMax };
    }

    private int[] findMin(int[][] rectangles, int coordinate) {
        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;
        for (int[] rectangle : rectangles) {
            if (rectangle[coordinate] < min) {
                secondMin = min;
                min = rectangle[coordinate];
            } else {
                secondMin = Math.min(secondMin, rectangle[coordinate]);
            }
        }
        return new int[]{ min, secondMin };
    }
}

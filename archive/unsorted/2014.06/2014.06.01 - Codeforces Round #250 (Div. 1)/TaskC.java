package tasks;

import algoribrary.comparators.DoubleComparator;
import algoribrary.geometry.Point;
import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskC {
    private static final int MODULO = 1_000_000_007;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Point[] points = new Point[counter];
        for (int i = 0; i < counter; i++) points[i] = new Point(in.nextInt(), in.nextInt());
        boolean[][] diagonals = new boolean[counter][counter];
        // edges are diags too :)
        for (int i = 0; i < counter; i++)
            diagonals[(i + 1) % counter][i] = diagonals[i][(i + 1) % counter] = true;
        for (int i = 0; i < counter; i++) outer: for (int j = 0; j < i - 1; j++) {
            Point mid = points[i].add(points[j]).multiply(.5);
            if (!inside(mid, points)) continue outer;
            for (int k = 0; k < counter; k++) {
                if (k == i || k == j) continue;
                if (points[k].counterClockWise(points[i], points[j]) == 0) {
                    Point a = points[i].subtract(points[j]);
                    Point b = points[k].subtract(points[i]);
                    Point c = points[k].subtract(points[j]);
                    if (DoubleComparator.DEFAULT.compare(a.scalarProduct(b), 0) <= 0 &&
                            DoubleComparator.DEFAULT.compare(a.scalarProduct(c), 0) >= 0)
                        continue outer;
                }
                int l = (k + 1) % counter;
                if (l == i || l == j) continue;
                if (intersects(points[i], points[j], points[k], points[l])) continue outer;
            }
            diagonals[i][j] = diagonals[j][i] = true;
        }
        long[][] dp = new long[counter][counter];
        for (int second = 0; second < counter; second++) for (int first = second - 1; first >= 0; first--) {
            if (!diagonals[first][second]) continue;
            if (first == second - 1)
                dp[first][second] = 1;
            for (int third = first + 1; third < second; third++)
                if (diagonals[first][third] && diagonals[third][second])
                    dp[first][second] = (dp[first][second] + dp[first][third] * dp[third][second]) % MODULO;
        }
        out.println(dp[0][counter - 1]);
    }

    private boolean inside(Point point, Point[] polygon) {
        boolean inside = false;
        Point other = point.add(new Point(1_000_000_007, 1));
        for (int i = 0; i < polygon.length; i++) {
            if (intersects(point, other, polygon[i], polygon[(i + 1) % polygon.length]))
                inside = !inside;
        }
        return inside;
    }

    private boolean intersects(Point a, Point b, Point c, Point d) {
        return boundingBox(a, b, c, d) &&
                a.counterClockWise(b, c) * a.counterClockWise(b, d) <= 0 &&
                c.counterClockWise(d, a) * c.counterClockWise(d, b) <= 0;
    }

    private boolean boundingBox(Point a, Point b, Point c, Point d) {
        return checkBoundingBox(a.x, b.x, c.x, d.x) && checkBoundingBox(a.y, b.y, c.y, d.y);
    }

    private boolean checkBoundingBox(double a, double b, double c, double d) {
        if (a > b) { double t = a; a = b; b = t; }
        if (c > d) { double t = c; c = d; d = t; }
        return Math.max(a, c) <= Math.min(b, d);
    }
}

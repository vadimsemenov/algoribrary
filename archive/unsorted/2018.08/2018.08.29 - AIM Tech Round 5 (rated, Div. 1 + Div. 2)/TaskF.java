package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int queries = in.nextInt();
        Map<Long, Set<Point>> all = new HashMap<>();
        Map<Point, Integer> have = new HashMap<>();
        int qty = 0;
        for (int q = 0; q < queries; ++q) {
            int type = in.nextInt();
            Point point = new Point(in.nextInt(), in.nextInt());
            if (type == 1) {
                qty++;
                Set<Point> points = all.computeIfAbsent(point.hypot(), key -> new LinkedHashSet<>());
                for (Point other : points) {
                    Point origin = point.add(other).norm();
                    have.put(origin, 2 + have.getOrDefault(origin, 0));
                }
                Point origin = point.norm();
                have.put(origin, 1 + have.getOrDefault(origin, 0));
                points.add(point);
            } else if (type == 2) {
                qty--;
                Set<Point> points = all.get(point.hypot());
                points.remove(point);
                for (Point other : points) {
                    Point origin = point.add(other).norm();
                    have.put(origin, have.get(origin) - 2);
                }
                Point origin = point.norm();
                have.put(origin, have.get(origin) - 1);
            } else if (type == 3) {
                out.println(qty - have.getOrDefault(point.norm(), 0));
            }
        }
    }

    static class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        long hypot() {
            return (long) x * x + (long) y * y;
        }

        Point add(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        Point norm() {
            int gcd = gcd(x, y);
            int xx = Math.abs(x);
            int yy = xx == x ? y : -y;
            if (gcd > 0) {
                xx /= gcd;
                yy /= gcd;
            }
            return new Point(xx, yy);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b > 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}

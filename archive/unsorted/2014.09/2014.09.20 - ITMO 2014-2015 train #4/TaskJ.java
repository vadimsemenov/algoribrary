package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskJ {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        Point a = new Point(in.nextInt(), in.nextInt(), in.nextInt());
        Point b = new Point(in.nextInt(), in.nextInt(), in.nextInt());
        Point c = new Point(in.nextInt(), in.nextInt(), in.nextInt());
        Point d = new Point(in.nextInt(), in.nextInt(), in.nextInt());
        Point ab = b.sub(a);
        Point cd = d.sub(c);
        if (ab.mul(cd) != 0) {
            out.println("Invalid");
        } else {
            long abc = signum(a.ccw(b, c));
            long abd = signum(a.ccw(b, d));
            long dcb = signum(d.ccw(c, b));
            long dca = signum(d.ccw(c, a));
            long pr = ab.mul(c.sub(a));
            System.err.println(abc + " " + abd + " " + dcb + " " + dca + " " + pr);
            // (ab, ac) >= |ab|^2
            if (pr < ab.hypot() || (abc != 0 && abc != abd) || (dcb != 0 && dcb != dca)) {
                out.println("Invalid");
            } else {
                out.println("Valid");
            }
        }
    }

    private long signum(long a) {
        return a > 0 ? 1 : (a < 0 ? -1 : 0);
    }

    static class Point {
        final long x, y, z;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }

        Point(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        long hypot() {
            return x * x + y * y + z * z;
        }
        Point add(Point other) {
            return new Point(this.x + other.x, this.y + other.y, this.z + other.z);
        }

        Point sub(Point other) {
            return new Point(this.x - other.x, this.y - other.y, this.z - other.z);
        }

        long mul(Point other) {
            return this.x * other.x + this.y * other.y + this.z * other.z;
        }

        /*
        ax ay az
        bx by bz
        cx cy cz
         */
        long ccw(Point b, Point c) {
            b = b.sub(this);
            c = c.sub(this);
            return b.x * c.y - b.y * c.x;
            /*
            return a.x * b.y * c.z - a.z * b.y * c.x +
                    a.y * b.z * c.x - a.y * b.x * c.z +
                    a.z * b.x * c.z - a.x * b.z * c.y;
            */
        }
    }
}

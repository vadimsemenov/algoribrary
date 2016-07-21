package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class TaskG {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[][] cs = new int[counter][3];
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < 3; ++j) {
                cs[i][j] = in.nextInt();
            }
        }
        Set<Line> lines = new HashSet<>();
        Set<Line> ans = new HashSet<>();
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < i; ++j) {
                long[] dir = new long[3];
                for (int k = 0; k < 3; ++k) {
                    dir[k] = cs[i][k] - cs[j][k];
                }
                int gcd = gcd((int) dir[0], gcd((int) dir[1], (int) dir[2]));
                for (int k = 0; k < 3; ++k) dir[k] /= gcd;
                if (dir[0] < 0) {
                    for (int k = 0; k < 3; ++k) dir[k] = -dir[k];
                } else if (dir[0] == 0 && dir[1] < 0) {
                    for (int k = 0; k < 3; ++k) dir[k] = -dir[k];
                } else if (dir[0] == 0 && dir[1] == 0 && dir[2] < 0) {
                    for (int k = 0; k < 3; ++k) dir[k] = -dir[k];
                }
                int left = -100000;
                int right = 100000;
                boolean foo;
                {
                    int middle = left;
                    long d1 = hypot(cs[i][0] + middle * dir[0], cs[i][1] + middle * dir[1], cs[i][2] + middle * dir[2]);
                    ++middle;
                    long d2 = hypot(cs[i][0] + middle * dir[0], cs[i][1] + middle * dir[1], cs[i][2] + middle * dir[2]);
                    --middle;
                    foo = d1 < d2;
                }
                while (right - left > 1) {
                    int middle = (left + right) / 2;
                    long d1 = hypot(cs[i][0] + middle * dir[0], cs[i][1] + middle * dir[1], cs[i][2] + middle * dir[2]);
                    ++middle;
                    long d2 = hypot(cs[i][0] + middle * dir[0], cs[i][1] + middle * dir[1], cs[i][2] + middle * dir[2]);
                    --middle;
                    if ((d1 > d2) ^ foo) {
                        left = middle;
                    } else {
                        right = middle;
                    }
                }
                Line line = new Line(
                        cs[i][0] + left * dir[0],
                        cs[i][1] + left * dir[1],
                        cs[i][2] + left * dir[2],
                        dir);
                if (lines.contains(line)) {
                    ans.add(line);
                } else {
                    lines.add(line);
                }
            }
        }
        System.err.println(ans);
        out.println(ans.size());
    }

    static class Line {
        long ox, oy, oz;
        long dx, dy, dz;

        @Override
        public String toString() {
            return "Line{" +
                    "ox=" + ox +
                    ", oy=" + oy +
                    ", oz=" + oz +
                    ", dx=" + dx +
                    ", dy=" + dy +
                    ", dz=" + dz +
                    '}';
        }

        public Line(long ox, long oy, long oz, long[] d) {
            this.ox = ox;
            this.oy = oy;
            this.oz = oz;
            this.dx = d[0];
            this.dy = d[1];
            this.dz = d[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;

            Line line = (Line) o;

            if (ox != line.ox) return false;
            if (oy != line.oy) return false;
            if (oz != line.oz) return false;
            if (dx != line.dx) return false;
            if (dy != line.dy) return false;
            return dz == line.dz;

        }

        @Override
        public int hashCode() {
            long result = ox;
            result = 31 * result + oy;
            result = 31 * result + oz;
            result = 31 * result + dx;
            result = 31 * result + dy;
            result = 31 * result + dz;
            return (int) (result % Integer.MAX_VALUE);
        }
    }
    private long hypot(long a, long b, long c) {
        return a * a + b * b + c * c;
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int d = a % b;
            a = b;
            b = d;
        }
        return a;
    }
}

package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

import static java.lang.Integer.signum;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int[][] fst = new int[4][2];
        int[][] snd = new int[4][2];
        for (int i = 0; i < 4; ++i) {
            for (int coord = 0; coord < 2; ++coord) {
                fst[i][coord] = 2 * in.nextInt();
            }
        }
        for (int i = 0; i < 4; ++i) {
            for (int coord = 0; coord < 2; ++coord) {
                snd[i][coord] = 2 * in.nextInt();
            }
        }
        if (inside(getMidPoint(fst), snd) || inside(getMidPoint(snd), fst)) {
            out.println("YES");
            return;
        }
        for (int[] p : fst) {
            if (inside(p, snd)) {
                out.println("YES");
                return;
            }
        }
        for (int[] p : snd) {
            if (inside(p, fst)) {
                out.println("YES");
                return;
            }
        }
        out.println("NO");
    }

    private int[] getMidPoint(int[][] square) {
        int[] mid = new int[2];
        for (int i = 0; i < 4; ++i) {
            mid[0] += square[i][0];
            mid[1] += square[i][1];
        }
        mid[0] /= 4;
        mid[1] /= 4;
        return mid;
    }

    private boolean inside(int[] point, int[][] square) {
        for (int i = 0; i < 4; ++i) {
            int j = (i + 1) % 4;
            if (between(square[i], square[j], point)) {
                return true;
            }
        }
        boolean predicate = ccw(square[3], square[0], point);
        for (int i = 0; i < 3; ++i) {
            if (predicate != ccw(square[i], square[i + 1], point)) {
                return false;
            }
        }
        return true;
    }

    private boolean ccw(int[] a, int[] b, int[] c) {
        return (b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1]) > 0;
    }

    private boolean between(int[] a, int[] b, int[] p) {
        if (Arrays.equals(a, p) || Arrays.equals(b, p)) {
            return true;
        }
        return (b[0] - a[0]) * (p[1] - a[1]) == (b[1] - a[1]) * (p[0] - a[0]) &&
                signum(p[0] - a[0]) == signum(b[0] - p[0]) &&
                signum(p[1] - a[1]) == signum(b[1] - p[1]);
    }
}

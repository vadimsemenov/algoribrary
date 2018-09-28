package task;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] fst = in.nextIntTable(n, 2);
        int[][] snd = in.nextIntTable(m, 2);
        int fstHullLength = convexHull(fst);
        int sndHullLength = convexHull(snd);
        if (fstHullLength != sndHullLength) {
            out.println("NO");
            return;
        }
        long[] sequence = new long[fstHullLength * 6 - 1];
        int ptr = 0;
        for (int i = 0; i < fstHullLength; ++i) {
            sequence[ptr++] = squaredDistance(fst[i], fst[(i + 1) % fstHullLength]);
            sequence[ptr++] = squaredDistance(fst[i], fst[(i + 2) % fstHullLength]);
        }
        sequence[ptr++] = -1;
        for (int j = 0; j < 2; ++j) {
            for (int i = 0; i < sndHullLength - j; ++i) {
                sequence[ptr++] = squaredDistance(snd[i], snd[(i + 1) % sndHullLength]);
                sequence[ptr++] = squaredDistance(snd[i], snd[(i + 2) % sndHullLength]);
            }
        }
        int[] prefixFunction = prefixFunction(sequence);
        for (int i = fstHullLength * 2; i < prefixFunction.length; ++i) {
            if (prefixFunction[i] == fstHullLength * 2) {
                out.println("YES");
                return;
            }
        }
        out.println("NO");
    }

    private int convexHull(int[][] points) {
        Arrays.sort(points, Comparator
                .comparingInt((int[] point) -> point[0])
                .thenComparingInt(point -> point[1]));
        int ptr = 1;
        while (ptr < points.length && points[0][0] == points[ptr][0] && points[0][1] == points[ptr][1]) {
            ++ptr;
        }
        Arrays.sort(points, ptr, points.length, (int[] fst, int[] snd) -> {
            int cmp = ccw(points[0], fst, snd);
            if (cmp == 0) cmp = Long.compare(squaredDistance(points[0], fst), squaredDistance(points[0], snd));
            return cmp;
        });
        ptr = 1;
        for (int i = 1; i < points.length; ++i) {
            while (ptr > 1 && ccw(points[ptr - 2], points[ptr - 1], points[i]) >= 0) {
                --ptr;
            }
            points[ptr++] = points[i];
        }
        return ptr;
    }

    private long squaredDistance(int[] a, int[] b) {
        long dx = a[0] - b[0];
        long dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }

    private int ccw(int[] a, int[] b, int[] c) {
        long x1 = b[0] - a[0];
        long y1 = b[1] - a[1];
        long x2 = c[0] - a[0];
        long y2 = c[1] - a[1];
        long vp = x1 * y2 - x2 * y1;
        return vp > 0 ? 1 : vp < 0 ? -1 : 0;
    }

    private int[] prefixFunction(long[] sequence) {
        int[] pf = new int[sequence.length];
        for (int i = 1, k = 0; i < sequence.length; ++i) {
            while (k > 0 && sequence[i] != sequence[k]) {
                k = pf[k - 1];
            }
            if (sequence[i] == sequence[k]) {
                ++k;
            }
            pf[i] = k;
        }
        return pf;
    }
}

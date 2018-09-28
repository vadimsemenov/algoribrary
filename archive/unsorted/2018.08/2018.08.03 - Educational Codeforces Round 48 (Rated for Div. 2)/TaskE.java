package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskE {
    private long[][] segments;
    private long[] sum;

    public void solve(int __, InputReader in, PrintWriter out) {
        long y0 = in.nextInt();
        long a = in.nextInt();
        long b = in.nextInt();
        int segmentsQty = in.nextInt();
        segments = new long[segmentsQty + 1][2];
        for (int i = 1; i <= segmentsQty; ++i) {
            for (int j = 0; j < 2; ++j) {
                segments[i][j] = in.nextInt();
            }
        }
        sum = new long[segmentsQty + 1];
        for (int i = 1; i <= segmentsQty; ++i) {
            sum[i] = sum[i - 1] + segments[i][1] - segments[i][0];
        }
        int qty = in.nextInt();
        for (int i = 0; i < qty; ++i) {
            int x = in.nextInt();
            int y = in.nextInt();
            long yy = y - y0;
            long xa = a * y - x * y0;
            long xb = b * y - x * y0;
            long shadeSize = getShadeSize(xb, yy) - getShadeSize(xa, yy);
            out.printf("%.15f\n", (double) shadeSize * (b - a) / (xb - xa));
        }
    }

    private long getShadeSize(long x, long div) {
        int ptr = 0;
        int upper = segments.length;
        while (upper - ptr > 1) {
            int mid = (ptr + upper) >>> 1;
            if (segments[mid][0] * div <= x) {
                ptr = mid;
            } else {
                upper = mid;
            }
        }
        long shade = 0;
        if (segments[ptr][1] * div > x) {
            shade += x - segments[ptr][0] * div;
            ptr--;
        }
        return shade + sum[ptr] * div;
    }
}

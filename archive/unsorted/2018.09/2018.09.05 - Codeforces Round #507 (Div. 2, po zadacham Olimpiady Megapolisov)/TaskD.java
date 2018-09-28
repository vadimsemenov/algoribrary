package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Random;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        final long min = 1;
        final long max = in.nextLong();
        final int step = in.nextInt();
        final int threshold = (step + 1) * 5;
        long left = min;
        long right = max;
        Random rng = new Random(Runtime.getRuntime().freeMemory() * (System.currentTimeMillis() + 1));
        while (true) {
            left = Math.max(left - step, min);
            right = Math.min(right + step, max);
            long mid = (left + right) >>> 1;
            if (right - left + 1 <= threshold) {
                long point = rng.nextInt((int) (right - left + 1)) + left;
                if (query(point, point, in, out)) {
                    return;
                }
            } else if (rng.nextBoolean()) {
                if (query(left, mid, in, out)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (query(mid, right, in, out)) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
        }
    }

    private boolean query(long left, long right, InputReader in, PrintWriter out) {
        out.println(left + " " + right);
        out.flush();
        String result = in.next();
        switch (result) {
            case "Yes": return true;
            case "No": return false;
            case "Bad": System.exit(0);
            default: throw new RuntimeException("POLUNDRA");
        }
    }
}

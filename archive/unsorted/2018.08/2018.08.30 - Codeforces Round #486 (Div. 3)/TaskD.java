package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        long[] xs = in.nextLongArray(counter);
        ArrayUtils.sort(xs);
        long min = xs[0];
        long max = xs[counter - 1];
        int answer = 1;
        long fst = min;
        long snd = Long.MIN_VALUE;
        long trd = Long.MIN_VALUE;
        outer:
        for (int i = 0; i < counter; ++i) {
            long x = xs[i];
            int left = i;
            int right = i + 1;
            for (long dist = 1; x - dist >= min || x + dist <= max; dist <<= 1) {
                int yi = left > 0 ? Arrays.binarySearch(xs, 0, left, x - dist) : -1;
                int zi = right < counter ? Arrays.binarySearch(xs, right, counter, x + dist) : (-counter - 1);
                left = yi < 0 ? -yi - 1 : yi;
                right = zi < 0 ? -zi - 1 : zi + 1;
                if (yi >= 0 && zi >= 0) {
                    answer = 3;
                    fst = xs[yi];
                    snd = x;
                    trd = xs[zi];
                    break outer;
                } else if (yi >= 0 || zi >= 0 && answer < 2) {
                    answer = 2;
                    fst = x;
                    snd = yi >= 0 ? xs[yi] : xs[zi];
                }
            }
        }
        out.println(answer);
        out.print(fst);
        if (snd != Long.MIN_VALUE) {
            out.print(" " + snd);
            if (trd != Long.MIN_VALUE) {
                out.print(" " + trd);
            }
        }
    }
}

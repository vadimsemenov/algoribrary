package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int queries = in.nextInt();
        int[] distance = new int[counter];
        int[] height = new int[counter];
        for (int i = 0; i < counter; ++i) {
            distance[i] = in.nextInt();
        }
        for (int i = 0; i < counter; ++i) {
            height[i] = in.nextInt();
        }
        long[] sums = new long[2 * counter];
        for (int i = 1; i < 2 * counter; ++i) {
            sums[i] = sums[i - 1] + distance[(i - 1) % counter];
        }
        long[] fst = new long[2 * counter];
        long[] snd = new long[2 * counter];
        for (int i = 0; i < 2 * counter; ++i) {
            fst[i] = -(sums[i] + 2 * height[i % counter]);
            snd[i] = sums[i] - 2 * height[i % counter];
        }
        IntervalTree max = new IntervalTree(fst);
        IntervalTree min = new IntervalTree(snd);
//        for (int i = max.size; i < max.size + fst.length; ++i) {
//            System.err.println(-max.data[i] + " " + min.data[i]);
//        }
        for (int q = 0; q < queries; ++q) {
            int begin = in.nextInt() - 1;
            int end = in.nextInt() - 1;
            int from, to;
            if (begin <= end) {
                from = end + 1;
                to = begin + counter;
            } else {
                from = end + 1;
                to = begin;
            }

            min.query(from, to);
            max.query(min.IDX + 1, to);
            long answer = -(max.VAL + min.VAL);
            max.query(from, to);
            min.query(from, max.IDX);
            answer = Math.max(answer, -(max.VAL + min.VAL));
            out.println(answer);
        }
    }

    static class IntervalTree {
        long[] data;
        int[] idx;
        int size;

        IntervalTree(long[] initial) {
            size = Integer.highestOneBit(initial.length);
            if ((initial.length & initial.length - 1) != 0) size <<= 1;
            idx = new int[size << 1];
            data = new long[size << 1];
            Arrays.fill(data, Long.MAX_VALUE / 3);
            for (int i = 0; i < initial.length; ++i) {
                data[i + size] = initial[i];
                idx[i + size] = i;
            }
            for (int i = size - 1; i > 0; --i) {
                int l = (i << 1);
                int r = (i << 1) | 1;
                data[i] = Math.min(data[l], data[r]);
                idx[i] = idx[l];
                if (data[l] > data[r]) {
                    idx[i] = idx[r];
                }
            }
        }

        int IDX;
        long VAL;

        void query(int from, int to) {
            from += size;
            to += size - 1;
            IDX = -1;
            VAL = Long.MAX_VALUE / 3;
            while (from <= to) {
                if ((from & 1) == 1) {
                    if (VAL > data[from]) {
                        VAL = data[from];
                        IDX = idx[from];
                    }
                    from++;
                }
                if ((to & 1) == 0) {
                    if (VAL > data[to]) {
                        VAL = data[to];
                        IDX = idx[to];
                    }
                    to--;
                }
                from >>>= 1;
                to >>>= 1;
            }
        }
    }
}

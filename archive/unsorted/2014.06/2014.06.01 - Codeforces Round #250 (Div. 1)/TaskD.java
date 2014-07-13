package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int queries = in.nextInt();
        int[] initial = new int[counter];
        for (int i = 0; i < counter; i++) initial[i] = in.nextInt();
        SegmentTree tree = new SegmentTree(initial);
        for (int query = 0; query < queries; query++) {
            int type = in.nextInt();
            if (type == 1) {
                int from = in.nextInt() - 1;
                int to = in.nextInt();
                out.println(tree.sumQuery(from, to));
            } else if (type == 2) {
                int from = in.nextInt() - 1;
                int to = in.nextInt();
                int mod = in.nextInt();
                tree.modQuery(from, to, mod);
            } else if (type == 3) {
                int at = in.nextInt() - 1;
                int value = in.nextInt();
                tree.updateQuery(at, value);
            } else throw new AssertionError(type);
        }
    }
}

class SegmentTree {
    private int size;
    private long[] data;
    private long[] max;

    public SegmentTree(int[] data) {
        size = Integer.highestOneBit(data.length) << 1;
        this.data = new long[(size << 1)];
        this.max = new long[(size << 1)];
        Arrays.fill(this.max, Long.MIN_VALUE);
        for (int i = 0; i < data.length; i++) {
            this.data[size + i] = data[i];
            this.max[size + i] = data[i];
        }
        for (int i = size - 1; i > 0; i--) {
            this.data[i] = this.data[i << 1] + this.data[(i << 1) | 1];
            this.max[i] = Math.max(this.max[i << 1], this.max[(i << 1) | 1]);
        }
    }

    public void updateQuery(int at, long value) {
        internalUpdateQuery(1, at, 0, size, value);
    }

    private void internalUpdateQuery(int index, int at, int left, int right, long value) {
        if (left > at || right <= at) return;
        if (right - left == 1) {
            data[index] = value;
            max[index] = value;
            return;
        }
        int middle = (left + right) >>> 1;
        internalUpdateQuery(index << 1, at, left, middle, value);
        internalUpdateQuery((index << 1) | 1, at, middle, right, value);
        recalc(index);
    }

    public void modQuery(int from, int to, int mod) {
        internalModQuery(1, from, to, 0, size, mod);
    }

    private void internalModQuery(int index, int from, int to, int left, int right, int mod) {
        if (to <= left || right <= from) return;
        if (right - left == 1) {
            data[index] %= mod;
            max[index] = data[index];
            return;
        }
        int middle = (left + right) >>> 1;
        if (max[index << 1] >= mod) internalModQuery(index << 1, from, to, left, middle, mod);
        if (max[(index << 1) | 1] >= mod) internalModQuery((index << 1) | 1, from, to, middle, right, mod);
        recalc(index);
    }

    public long sumQuery(int from, int to) {
        return internalSumQuery(1, from, to, 0, size);
    }

    private long internalSumQuery(int index, int from, int to, int left, int right) {
        if (right <= from || to <= left) return 0;
        if (from <= left && right <= to) return data[index];
        int middle = (left + right) >>> 1;
        return internalSumQuery(index << 1, from, to, left, middle) +
                internalSumQuery((index << 1) | 1, from, to, middle, right);
    }

    private void recalc(int idx) {
        data[idx] = data[idx << 1] + data[(idx << 1) | 1];
        max[idx] = Math.max(max[idx << 1], max[(idx << 1) | 1]);
    }
}

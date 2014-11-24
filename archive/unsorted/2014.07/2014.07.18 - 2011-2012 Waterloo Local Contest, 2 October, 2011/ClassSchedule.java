package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class ClassSchedule {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int categories = in.nextInt();
        int classes = in.nextInt();
        int locations = in.nextInt();
        int[] endsIn = new int[classes];
        int[] energy = new int[classes];
        int[] position = new int[classes];
        for (int i = 0; i < classes; i++) {
            position[i] = in.nextInt();
            energy[i] = in.nextInt();
            endsIn[i] = energy[i] + position[i];
            int
        }

        for (int category = 1; category < categories; category++) {
            int[] previous = endsIn;
            endsIn = new int[classes];
            int[] previousPosition = position;
            position = new int[classes];
            for (int i = 0; i < classes; i++) {
                position[i] = in.nextInt();
                energy[i] = in.nextInt();
            }
            Arrays.fill(endsIn, Integer.MAX_VALUE);
            for (int clazz = 0; clazz < classes; clazz++) {
                for (int prev = 0; prev < classes; prev++) {
                    endsIn[clazz] = Math.min(endsIn[clazz],
                            previous[prev] + Math.abs(previousPosition[prev] - position[clazz]));
                }
                endsIn[clazz] += energy[clazz];
            }
        }
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < classes; i++) {
            answer = Math.min(answer, locations - position[i] + endsIn[i]);
        }
        out.println(answer);
    }
}

class SegmentTree {
    private int[] update, min;
    private int size;

    public SegmentTree(int size) {
        this.size = Integer.highestOneBit(size) << (1 ^ (size & size - 1));
        update = new int[this.size + 1];
        min = new int[this.size + 1];
        Arrays.fill(min, Integer.MAX_VALUE);
    }

    public void update(int from, int to, int by) {
        internalUpdate(1, 0, size, from, to, by);
    }

    private void internalUpdate(int idx, int left, int right, int from, int to, int by) {
        if (to <= left || right <= from) return;
        if (from <= left && right <= to) {
            update[idx] += by;
            return;
        }
        push(idx);
        int middle = (left + right) >>> 1;
        internalUpdate(idx << 1, left, middle, from, to, by);
        internalUpdate((idx << 1) | 1, middle, right, from, to, by);
        update[idx] = 0;
        min[idx] = Math.min(getMin(idx << 1), getMin((idx << 1) | 1));
    }

    public void assign(int at, int val) {
        internalAssign(1, 0, size, at, val);
    }

    private void internalAssign(int idx, int left, int right, int at, int val) {
        if (at < left || right <= at) return;
        if (at == left && at + 1 == right) {
            min[idx] = val;
            update[idx] = 0;
        }
        push(idx);
        int middle = (left + right) >>> 1;
        internalAssign(idx << 1, left, middle, at, val);
        internalAssign((idx << 1) | 1, middle, right, at, val);
        min[idx] = Math.min(getMin(idx << 1), getMin((idx << 1) | 1));
    }

    public int min(int from, int to) {
        return internalMin(1, 0, size, from, to);
    }

    private int internalMin(int idx, int left, int right, int from, int to) {
        if (to <= left || right <= from) return Integer.MAX_VALUE;
        if (left <= from && right <= to) return getMin(idx);
        push(idx);
        int middle = (left + right) >>> 1;
        return Math.min(internalMin(idx << 1, left, middle, from, to),
                internalMin((idx << 1) | 1, middle, right, from, to));
    }

    private void push(int idx) {
        update[idx << 1] += update[idx];
        update[(idx << 1) | 1] += update[idx];
        min[idx] += update[idx];
        update[idx] = 0;
    }

    private int getMin(int idx) {
        return min[idx] + update[idx];
    }
}

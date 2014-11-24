package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskJ {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] cnt = new int[counter];
        for (int i = 0; i < counter; ++i) cnt[i] = in.nextInt();
        int[] cap = Arrays.copyOf(cnt, cnt.length);
        int parent = counter - 1;
        int[] answer = new int[counter - 1];
        for (int i = counter - 1; i >= 0; --i) {
            if ()
        }
    }

    static class MaxIntervalTree {
        int[] data;
        int[] idx;
        int size;

        MaxIntervalTree(int size) {
            this.size = Integer.highestOneBit(size);
            data = new int[size << 1];
            Arrays.fill(data, Integer.MIN_VALUE);
            for (int i = 0; i < size; ++i) {
                idx[i + size] = i;
            }
            for (int i = size - 1; i > 0; --i) {
                idx[i] = idx[i << 1];
            }
        }

        void update(int at, int val) {
            at += size;
            data[at] = val;
            at >>= 1;
            while (at > 0) {
                int h = at << 1;
                if (data[h + 1] > data[h]) ++h;
                data[at] = data[h];
                idx[at] = idx[h];
            }
        }

    }
}

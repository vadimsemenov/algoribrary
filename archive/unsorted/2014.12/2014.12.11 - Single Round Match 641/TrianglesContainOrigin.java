package tasks;

import java.util.Arrays;
import java.util.Comparator;

public class TrianglesContainOrigin {
    // O(n log n) solution
    public long count(int[] x, int[] y) {
        int counter = x.length;
        final double[] angles = new double[counter];
        final double[] inv = new double[counter];
        Integer[] order = new Integer[counter];
        for (int i = 0; i < counter; ++i) {
            angles[i] = Math.atan2(y[i], x[i]);
            inv[i] = Math.atan2(-y[i], -x[i]);
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Double.compare(inv[o1], inv[o2]);
            }
        });
        Arrays.sort(angles);

        int ptr1 = -1;
        int ptr2 = 0;
        long answer = 0;
        for (int i = 0; i < counter; ++i) {
            while (ptr1 < i && inv[order[i]] - inv[order[ptr1 + 1]] > Math.PI) {
                ++ptr1;
            }
            while (ptr2 < counter && inv[order[ptr2]] - inv[order[i]] < Math.PI) {
                ++ptr2;
            }
            int get = Arrays.binarySearch(angles, inv[order[i]]);
            if (get < 0) get = -get;
            answer += (i - ptr1 - 1) * get;
            answer += (ptr1 + 1) * (counter - get);
            answer -= (ptr2 - i - 1) * get;
            answer += (counter - ptr2) * get;
        }
        return answer / 3;
    }

    public long count0(final int[] x, final int[] y) {
        final Integer[] indices = new Integer[x.length];
        for (int i = 0; i < indices.length; ++i) {
            indices[i] = i;
        }
        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Double.compare(Math.atan2(y[o1], x[o1]), Math.atan2(y[o2], x[o2]));
            }
        });
        long answer = 0;
        int from = 1;
        for (int i = 0; i < indices.length; ++i) {
            while (from < indices.length && x[indices[i]] * y[indices[from]] - y[indices[i]] * x[indices[from]] >= 0) {
                ++from;
            }
            int to = from;
            for (int j = i + 1; j < from; ++j) {
                while (to < indices.length && x[indices[j]] * y[indices[to]] - y[indices[j]] * x[indices[to]] > 0) {
                    ++to;
                }
                answer += to - from;
            }
        }
        return answer;
    }
}

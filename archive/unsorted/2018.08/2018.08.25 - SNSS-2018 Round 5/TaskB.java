package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskB {
    private int[][] params;
    private Comparator<Integer> angleComparator = (fst, snd) ->
            ccw(params[fst][0], params[fst][1], params[snd][0], params[snd][1]);
    private Comparator<Integer> radiusComparator = Comparator.comparingInt(this::diagonal);

    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        params = in.nextIntTable(counter, 2);
        Integer[] byAngle = new Integer[counter];
        Integer[] byRadius = new Integer[counter];
        for (int i = 0; i < counter; ++i) {
            byAngle[i] = i;
            byRadius[i] = i;
        }
        Arrays.sort(byAngle, angleComparator.thenComparing(radiusComparator));
        Arrays.sort(byRadius, radiusComparator.reversed().thenComparing(angleComparator));
        int[] id = new int[counter];
        for (int i = 0; i < counter; ++i) {
            id[byAngle[i]] = i;
        }
        MaxSegmentTree tree = new MaxSegmentTree(counter);
        int[] answer = new int[counter];
        for (int i = 0; i < counter; ) {
            int j = i + 1;
            while (j < counter && radiusComparator.compare(byRadius[i], byRadius[j]) == 0) {
                j++;
            }
            int diff = diagonal(byRadius[0]) - diagonal(byRadius[i]);
            for (int k = i; k < j; ++k) {
                int left = upperBound(params[k][0] + diff, params[k][1], id);
                int right = lowerBound(params[k][0], params[k][1] + diff, id);
                answer[k] = tree.get(left, right);
            }
            for (int k = i; k < j; ++k) {
                tree.update(byRadius[k], answer[k]);
            }
            i = j;
        }
    }

    private int lowerBound(int x, int y, int[] id) {
        int left = -1;
        int right = id.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            int[] point = params[id[mid]];
            if (ccw(point[0], point[1], x, y) < 0) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int upperBound(int x, int y, int[] id) {
        int left = -1;
        int right = id.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            int[] point = params[id[mid]];
            if (ccw(point[0], point[1], x, y) <= 0) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    private int ccw(int x1, int y1, int x2, int y2) {
        return Long.signum((long) x1 * y2 - (long) x2 * y1);
    }

    private int diagonal(int i) {
        return params[i][0] + params[i][1];
    }

    static class MaxSegmentTree {
        int[] tree;

        MaxSegmentTree(int capacity) {
            tree = new int[Integer.highestOneBit(capacity) << 2];
        }

        void update(int at, int value) {
            at += tree.length >> 1;
            while (at > 0) {
                tree[at] = Math.max(tree[at], value);
                at >>= 1;
            }
        }

        int get(int from, int to) {
            from += tree.length >> 1;
            to += tree.length >> 1;
            int max = 0;
            while (from <= to) {
                if ((from & 1) == 1) {
                    max = Math.max(max, tree[from]);
                    from++;
                }
                if ((to & 1) == 0) {
                    max = Math.max(max, tree[to]);
                    to--;
                }
                from >>= 1;
                to >>= 1;
            }
            return max;
        }
    }
}
